package com.moxian.quartz;

import static javax.ws.rs.client.ClientBuilder.newClient;
import static org.glassfish.jersey.apache.connector.ApacheClientProperties.CONNECTION_MANAGER;
import static org.glassfish.jersey.client.ClientProperties.CONNECT_TIMEOUT;
import static org.glassfish.jersey.client.ClientProperties.READ_TIMEOUT;
import static org.glassfish.jersey.servlet.ServletProperties.JAXRS_APPLICATION_CLASS;

import javax.inject.Inject;
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
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.moxian.quartz.dao.SensitiveWordsDao;
import com.moxian.quartz.dao.impl.SensitiveWordsDaoMybatisImpl;
import com.moxian.quartz.dao.impl.SensitiveWordsMapper;
import com.moxian.quartz.service.testService;
import com.moxian.quartz.service.impl.testServiceImpl;
import com.moxian.quartz.util.JerseyApplication;
import com.moxian.quartz.util.JerseySwaggerServlet;
import com.moxian.quartz.validation.SensitiveWordsInitializer;

@Slf4j
@Configuration
public class BeanConfig {
    @Value("${spring.profiles.active:dev}")
    protected String activeSpringProfiles;
    @Value("${webClient.connectionPool.maxTotal}")
    protected int webClientConnectionPoolMaxTotal;
    @Value("${webClient.connectionPool.defaultMaxPerRoute}")
    protected int webClientConnectionPoolDefaultMaxPerRoute;
    @Inject
    private MxDataSourceConfig mxDataSourceConfig;


    @Bean
    public Filter gzipFilter() {
        return new GzipFilter();
    }

    @Bean
    public ServletRegistrationBean jaxrsServlet() {
        final JerseySwaggerServlet servlet = new JerseySwaggerServlet();
        final ServletRegistrationBean registrationBean = new ServletRegistrationBean(servlet, "/m1/*");
        registrationBean.addInitParameter(JAXRS_APPLICATION_CLASS, JerseyApplication.class.getName());
        return registrationBean;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // Auto-detect `JSR310Module`...
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false); // -> ISO string serialization
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
        poolingConnectionManager.setDefaultMaxPerRoute(webClientConnectionPoolDefaultMaxPerRoute);

        final ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(READ_TIMEOUT, 2000);
        clientConfig.property(CONNECT_TIMEOUT, 500);
        clientConfig.property(CONNECTION_MANAGER, poolingConnectionManager);
        clientConfig.connectorProvider(new ApacheConnectorProvider());

        return newClient(clientConfig)
                .register(jacksonJsonProvider());
    }
    

    // Datasource for running in embedded application container such as Jetty
    @ConditionalOnClass(name = "org.eclipse.jetty.server.Server")
    @Bean
    public DataSource dataSource() {
        log.info("MX dataSource URL : {}", mxDataSourceConfig.getUrl());
        return DataSourceBuilder
                .create(getClass().getClassLoader())
                .url(mxDataSourceConfig.getUrl())
                .username(mxDataSourceConfig.getUsername())
                .password(mxDataSourceConfig.getPassword())
                .build();
    }

    // JNDI datasource for running in external application container such as Tomcat
//    @ConditionalOnClass(name = "org.apache.catalina.startup.Tomcat")
    @Bean
    public DataSource jndiDataSource() {
        /*JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(mxDataSourceConfig.getJndi());
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        try {
            bean.afterPropertiesSet();
        } catch (NamingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
         return (DataSource)bean.getObject();
         */
        return DataSourceBuilder
                .create(getClass().getClassLoader())
                .url(mxDataSourceConfig.getUrl())
                .username(mxDataSourceConfig.getUsername())
                .password(mxDataSourceConfig.getPassword())
                .build();

    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(jndiDataSource());
        return sqlSessionFactoryBean;
    }

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

    ///////////////////////////////////////////////////////////////////////////////////////
    // Service bean configuration
    ///////////////////////////////////////////////////////////////////////////////////////
    @Bean
    public testService testService(){
    	return new testServiceImpl();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // DAO bean configuration
    ///////////////////////////////////////////////////////////////////////////////////////
	@Primary
	@Bean
	public SensitiveWordsDao sensetiveWordsDao() throws Exception {
		return new SensitiveWordsDaoMybatisImpl(sensetiveWordsMapper().getObject());
	}

	@Bean
	public MapperFactoryBean<SensitiveWordsMapper> sensetiveWordsMapper()
			throws Exception {
		MapperFactoryBean<SensitiveWordsMapper> mapperFactoryBean = new MapperFactoryBean<>();
		mapperFactoryBean.setMapperInterface(SensitiveWordsMapper.class);
		mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean()
				.getObject());
		return mapperFactoryBean;
	}
	
	@Bean
	public SensitiveWordsInitializer sensetiveWordsInitializer() {
		return new SensitiveWordsInitializer();
	}
	
	@Bean
	public LoggingAspect loggingAspect() {
		return new LoggingAspect();
	}
}
