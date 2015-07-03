package com.moxian.common.login.dao.impl;

import java.io.Serializable;

import javax.inject.Inject;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.moxian.common.login.dao.TokenRedisDao;
import com.moxian.common.login.domain.Auth;

public class TokenRedisDaoImpl implements TokenRedisDao {

    @Inject
    private RedisTemplate<Serializable,Serializable> redisTemplate;
    
    /**
     * auth对象存入缓存
     */
    public void save(final Auth auth) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(
                        redisTemplate.getStringSerializer().serialize(
                                "token.userid."
                                        + String.valueOf(auth.getUserId())),
                        redisTemplate.getStringSerializer().serialize(
                                auth.getToken())
                        );
                return null;
            }
        });
    }

    /**
     * 根据userId读取redis中的auth对象
     */
    public Auth read(final long userId) {
        return (Auth) redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Auth doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key = redisTemplate.getStringSerializer().serialize("token.userid." + userId);
                if (connection.exists(key)) {
                    byte[] value = connection.get(key);
                    String token = redisTemplate.getStringSerializer().deserialize(value);

                    Auth auth = Auth.builder().token(token).userId(userId).build();
                    return auth;
                }
                return null;
            }
        });
    }
    
    public void destroyAuth(final long userId){
    	redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection)
					throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize("token.userid." + userId);
                if (connection.exists(key)) {
                	connection.del(key);
                }
				return null;
			}
    		
    	});
    }
}
