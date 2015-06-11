package com.moxian.common;

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
import com.moxian.common.bo.FileUploadConfig;
import com.moxian.common.dao.FileDao;
import com.moxian.common.dao.impl.FileDaoMyBatisImpl;
import com.moxian.common.dao.impl.FileMapper;
import com.moxian.common.proxy.ThriftServerProxy;
import com.moxian.common.proxy.impl.FileServerThriftImpl;
import com.moxian.common.service.FileService;
import com.moxian.common.service.impl.FileServiceImpl;
import com.moxian.common.util.JerseyApplication;
import com.moxian.common.util.JerseySwaggerServlet;


@SuppressWarnings("deprecation")
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
    
    private DataSource dataSource;
    
    @Bean
    public Filter gzipFilter() {
        return new GzipFilter();
    }
    
    @Component
	@ConfigurationProperties(prefix = "spring.ds.moxian")
	public static class MxDataSourceConfig extends DataSourceConfig {
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
    
    @Component
	@ConfigurationProperties(prefix = "thrift.server.config")
	@Data
	public static class ThriftServerConfig {
		private String host;
		private int port;
	}

    // Datasource for running in embedded application container such as Jetty
    @ConditionalOnClass(name = "org.eclipse.jetty.server.Server")
    @Bean
    public DataSource dataSource() {
        log.info("MX dataSource URL : {}", mxDataSourceConfig.getUrl());
        dataSource = DataSourceBuilder
                .create(getClass().getClassLoader())
                .url(mxDataSourceConfig.getUrl())
                .username(mxDataSourceConfig.getUsername())
                .password(mxDataSourceConfig.getPassword())
                .build();
        return dataSource;
    }

//     JNDI datasource for running in external application container such as Tomcat
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

//    @Component
//    @ConfigurationProperties(prefix = "spring.ds.moxian")
//    public static class MxDataSourceConfig extends DataSourceConfig {
//    }

    @Data
    static class DataSourceConfig {
        private String url;
        private String username;
        private String password;
        private String jndi;
    }
    
    @Component
    @ConfigurationProperties(prefix = "spring.fileupload.moxian")
    public static class MxUploadFileConfig extends FileConfig{
     
    }
    
    @Data
    static class FileConfig {
        private String filetype;
        private String basepath;
        private String fileClassification;
        private String domain;
    }
    
    
    @Bean
    FileServerThriftImpl fileServerThriftImpl(){
    	return new FileServerThriftImpl();
    }
    
    
    @Bean
	public ThriftServerProxy thriftServerProxy() {
		return new ThriftServerProxy();
	}

    ///////////////////////////////////////////////////////////////////////////////////////
    // Service bean configuration
    ///////////////////////////////////////////////////////////////////////////////////////
//    @Bean
//    public UserService userService() {
//        return new UserServiceImpl();
//    }
    @Bean 
    public FileUploadConfig fileUploadConfig(){
    	return new FileUploadConfig();
    }
    
    
    @Bean
    public FileService fileService(){
    	return new FileServiceImpl();
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    // DAO bean configuration
    ///////////////////////////////////////////////////////////////////////////////////////
//    @Bean
//    public UserDao userDaoJpa() {
//        return new UserDaoJpaImpl();
//    }
    

    @Primary
    @Bean
    public FileDao fileDaoMyBatis() throws Exception{
    	return new FileDaoMyBatisImpl(fileMapper().getObject());
    }
    
    @Bean
    public MapperFactoryBean<FileMapper> fileMapper() throws Exception {
        MapperFactoryBean<FileMapper> mapperFactoryBean = new MapperFactoryBean<>();
        mapperFactoryBean.setMapperInterface(FileMapper.class);
        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean().getObject());
        return mapperFactoryBean;
    }
    
//    @Primary
//    @Bean
//    public UserDao userDaoMyBatis() throws Exception {
//        return new UserDaoMyBatisImpl(userMapper().getObject());
//    }

//    @Bean
//    public MapperFactoryBean<UserMapper> userMapper() throws Exception {
//        MapperFactoryBean<UserMapper> mapperFactoryBean = new MapperFactoryBean<>();
//        mapperFactoryBean.setMapperInterface(UserMapper.class);
//        mapperFactoryBean.setSqlSessionFactory(sqlSessionFactoryBean().getObject());
//        return mapperFactoryBean;
//    }

    //===================================
    
}
