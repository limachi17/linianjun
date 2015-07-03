package com.moxian.common.login;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.glassfish.jersey.apache.connector.ApacheClientProperties.CONNECTION_MANAGER;
import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;
import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

import javax.inject.Inject;
import javax.naming.NamingException;
import javax.servlet.Filter;
import javax.sql.DataSource;
import javax.ws.rs.client.Client;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.eclipse.jetty.servlets.GzipFilter;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.moxian.common.login.dao.AbstractUserDao;
import com.moxian.common.login.dao.AuthDao;
import com.moxian.common.login.dao.impl.AbstractUserDaoMyBatisImpl;
import com.moxian.common.login.dao.impl.AbstractUserMapper;
import com.moxian.common.login.dao.impl.AuthDaoMyBatisImpl;
import com.moxian.common.login.dao.impl.AuthMapper;
import com.moxian.common.login.dao.impl.TokenRedisDaoImpl;
import com.moxian.common.login.proxy.server.LoginServerThrift.Processor;
import com.moxian.common.login.proxy.server.LoginServerThriftImpl;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.service.RegService;
import com.moxian.common.login.service.impl.AuthServiceImpl;
import com.moxian.common.login.service.impl.RegServiceImpl;
import com.moxian.common.login.util.JerseyApplication;
import com.moxian.common.login.util.JerseySwaggerServlet;
import com.moxian.common.thrift.server.ServerConfig;
import com.moxian.common.thrift.server.ThriftServerProxy;

@Slf4j
@Configuration
public class BeanConfig {
/*	@Value("${spring.profiles.active:dev}")
	protected String activeSpringProfiles;*/
	@Value("${webClient.connectionPool.maxTotal}")
	protected int webClientConnectionPoolMaxTotal;
	@Value("${webClient.connectionPool.defaultMaxPerRoute}")
	protected int webClientConnectionPoolDefaultMaxPerRoute;

	@Inject
	private MxDataSourceConfig mxDataSourceConfig;
	
	private DataSource dataSource;
	
	@Inject
	private WarFileConfig warFileConfig;
	
	@Inject
	private ThriftServerConfig thriftServerConfig;
	
	@SuppressWarnings("deprecation")
    @Bean
	public Filter gzipFilter() {
		return new GzipFilter();
	}

	@Bean
	public ServletRegistrationBean jaxrsServlet() {
		final JerseySwaggerServlet servlet = new JerseySwaggerServlet();
		final ServletRegistrationBean registrationBean = new ServletRegistrationBean(
				servlet, warFileConfig.getVersion());
		registrationBean.addInitParameter(JAXRS_APPLICATION_CLASS,
				JerseyApplication.class.getName());
		return registrationBean;
	}

	@Bean
	public ObjectMapper objectMapper() {
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.findAndRegisterModules(); // Auto-detect `JSR310Module`...
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
				false); // -> ISO string serialization
		return objectMapper;
	}

	@Bean
	public JacksonJsonProvider jacksonJsonProvider() {
		return new JacksonJsonProvider(objectMapper());
	}

	@Bean
	public Client webClient() {
		final PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
		poolingConnectionManager.setMaxTotal(webClientConnectionPoolMaxTotal);
		poolingConnectionManager
				.setDefaultMaxPerRoute(webClientConnectionPoolDefaultMaxPerRoute);

		final ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(READ_TIMEOUT, 2000);
		clientConfig.property(CONNECT_TIMEOUT, 500);
		clientConfig.property(CONNECTION_MANAGER, poolingConnectionManager);
		clientConfig.connectorProvider(new ApacheConnectorProvider());

		return newClient(clientConfig).register(jacksonJsonProvider());
	}

	// Datasource for running in embedded application container such as Jetty
	@ConditionalOnClass(name = "org.eclipse.jetty.server.Server")
	@Bean
	public DataSource dataSource() {
		log.info("MX dataSource URL : {}", mxDataSourceConfig.getUrl());
		dataSource = DataSourceBuilder.create(getClass().getClassLoader())
				.url(mxDataSourceConfig.getUrl())
				.username(mxDataSourceConfig.getUsername())
				.password(mxDataSourceConfig.getPassword()).build();
		return dataSource;
	}

	// JNDI datasource for running in external application container such as
	// Tomcat
	@ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
	@Bean
	public DataSource jndiDataSource() {
		JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
		bean.setJndiName(mxDataSourceConfig.getJndi());
		bean.setProxyInterface(DataSource.class);
		bean.setLookupOnStartup(false);
		try {
			bean.afterPropertiesSet();
		} catch (NamingException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		dataSource = (DataSource) bean.getObject();
		return dataSource;

	}

	@Bean
	public SqlSessionFactoryBean sqlSessionFactoryBean() {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean;
	}
	
	
	// /////////////////////////////////////////////////////////////////////////////////////
	// Project Configuration Setting Bean
	// /////////////////////////////////////////////////////////////////////////////////////
	
	@Component
	@ConfigurationProperties(prefix = "spring.ds.moxian")
	public static class MxDataSourceConfig extends DataSourceConfig {
	}

	@Data
	static class DataSourceConfig {
		private String url;
		private String username;
		private String password;
		private String jndi;
	}
	
	@Component
	@ConfigurationProperties(prefix = "project.warfile")
	@Data
	public static class WarFileConfig {
		private String version;
	}
	
	@Component
	@ConfigurationProperties(prefix = "thrift.server.config")
	public static class ThriftServerConfig extends ServerConfig{
		private String host;
		private int port;
		private int maxWorkerThreads;
	}
	

	
	/**
	 * Sam
	 * 
	 */
	// /////////////////////////////////////////////////////////////////////////////////////
	// Service bean configuration
	// /////////////////////////////////////////////////////////////////////////////////////

	@Bean
	public RegService userService() {
		return new RegServiceImpl();
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// Dao bean configuration
	// /////////////////////////////////////////////////////////////////////////////////////
	@Primary
	@Bean
	public AbstractUserDao userDaoMyBatis() throws Exception {
		return new AbstractUserDaoMyBatisImpl(abstractUserMapper().getObject());
	}

	@Bean
	public MapperFactoryBean<AbstractUserMapper> abstractUserMapper()
			throws Exception {
		MapperFactoryBean<AbstractUserMapper> mapperFactoryBean = new MapperFactoryBean<>();
		mapperFactoryBean.setMapperInterface(AbstractUserMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean()
				.getObject());
		return mapperFactoryBean;
	}

	/**
	 * hjl
	 * 
	 */
	// /////////////////////////////////////////////////////////////////////////////////////
	// Service bean configuration
	// /////////////////////////////////////////////////////////////////////////////////////
	@Bean
	public AuthService pageStartService() {
		return new AuthServiceImpl();
	}

	// /////////////////////////////////////////////////////////////////////////////////////
	// Dao bean configuration
	// /////////////////////////////////////////////////////////////////////////////////////
	@Primary
	@Bean
	public AuthDao pageStartDaoMyBatis() throws Exception {
		return new AuthDaoMyBatisImpl(pageStartMapper().getObject());
	}

	@Bean
	public MapperFactoryBean<AuthMapper> pageStartMapper() throws Exception {
		MapperFactoryBean<AuthMapper> mapperFactoryBean = new MapperFactoryBean<>();
		mapperFactoryBean.setMapperInterface(AuthMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean()
				.getObject());
		return mapperFactoryBean;
	}

	@Bean
	public ThriftServerProxy thriftServerProxy() {
		return new ThriftServerProxy(new Processor<LoginServerThriftImpl>(loginServerThriftImpl()),thriftServerConfig);
	}
	
	

	@Bean
	public LoginServerThriftImpl loginServerThriftImpl() {
		return new LoginServerThriftImpl();
	}
	@Bean
    public TokenRedisDaoImpl tokenDao() {
        return new TokenRedisDaoImpl();
    }
	
}
