package com.moxian.common.login;

import java.io.Serializable;

import javax.inject.Inject;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import redis.clients.jedis.JedisPoolConfig;

@Configuration  
@Slf4j
@EnableCaching  
public class RedisConfig {
	   
/*		private @Value("${redis.config.host}") String host;
		private @Value("${redis.config.port}") int port;*/
	
		@Inject
		private RedisConfigInfo redisConfigInfo;
		
		@Inject
		private RedisConnectionFactory redisConnectionFactory;

	    @Bean  
	    public RedisConnectionFactory redisConnectionFactory() {  
	    	log.info("########  init redisConnectionFactory Host: "+redisConfigInfo.getHost()+" Port: "+redisConfigInfo.getPort());
	        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();  
	        jedisConnectionFactory.setHostName(redisConfigInfo.getHost());  
	        jedisConnectionFactory.setPort(redisConfigInfo.getPort());
	        jedisConnectionFactory.setUsePool(redisConfigInfo.isUsePool());
	        jedisConnectionFactory.setDatabase(redisConfigInfo.getDatabaseIndex());
	        jedisConnectionFactory.setPoolConfig(redisConfigInfo);
	        return jedisConnectionFactory; 
	    }
	    
	    @Bean  
	    public RedisTemplate<Serializable, Serializable> redisTemplate() {  
	    	log.info("########  init redisTemplate"); 
	        RedisTemplate<Serializable, Serializable> redisTemplate = new RedisTemplate<Serializable, Serializable>(); 
	        redisTemplate.setConnectionFactory(redisConnectionFactory);  
	        return redisTemplate;  
	    }  
	     
	    
	    @Bean  
	    public RedisCacheManager redisCacheManager(RedisTemplate<Serializable, Serializable>redisTemplate) {  
	    	log.info("init redisCacheManager");
	        return new RedisCacheManager(redisTemplate);  
	    }  
	    
		@Component
		@ConfigurationProperties(prefix = "spring.redis.moxian")
		@Data
		public static class RedisConfigInfo extends JedisPoolConfig{
			private String host;
			private int port;
			private int databaseIndex;
			private boolean usePool;
			@Override
			public String toString() {
				return "RedisConfigInfo [host=" + host + ", port=" + port
						+ ", databaseIndex=" + databaseIndex + ", usePool="
						+ usePool + "]";
			}
			
		}
}
