package com.moxian.common.login.service.impl;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;

import com.moxian.common.login.dao.AuthDao;
import com.moxian.common.login.dao.TokenRedisDao;
import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.util.HashGenerator;
import com.moxian.common.login.util.Type;

@Slf4j
public class AuthServiceImpl implements AuthService {

	@Inject
    public AuthDao authDao;
	
	@Inject
	public TokenRedisDao tokenDao;
	
	@Override
	public boolean isValidTokenPare(long userId, String token) {
        final Auth auth = this.getToken(userId);
      
        boolean isValid = false;
        if(token.equals(auth.getToken())){
        	log.info("userId token validation success");
            isValid = true;
        }
         return isValid;
	}

	/**
	 * 
	* @Title: updateToken 
	* @param: 
	* @Description: token存入redis
	* @return void
	 */
    private void updateToken(Auth auth){
        tokenDao.save(auth);
    }
    
    /**
     * 根据userId与密码，验证登录
     */
	@Override
	public Auth isValidatePassPare(long userId, String userpass) {
		Long userIdInDb = authDao.login(userId, userpass);
		Auth auth = null;
		if (userIdInDb != null) {
			String token = this.createToken(userIdInDb);
			auth = Auth.builder().userId(userId).token(token).build();
            this.updateToken(auth);
		}
		return auth;
	}
	
	/**
	 * 
	* @Title: createToken 
	* @param: 
	* @Description: 根据userId生成token
	* @return String
	 */
	public String createToken(long userId){
	    String token = HashGenerator.generateToken(String.valueOf(userId)); 
	    return token;
	}
	
	@Override
	public Auth getToken(long userId) {
		// 创建一个token
	    //System.out.println("Auth getToken(long userId) ");

        Auth auth;
		String token;
        auth = tokenDao.read(userId);
        if(auth == null){
            token = this.createToken(userId);
            auth = Auth.builder().userId(userId).token(token).build();
        }
        return auth;
	}

	/**
     * @deprecated Use {@link com.moxian.common.login.dao.AuthDao#getUserIdByAccount(String)} instead
     */
    public long getUserIdByPhoneOrEmail(String userAccount) {
        return this.getUserIdByAccount(userAccount);
    }

    @Override
    public long getUserIdByAccount(String userAccount){
        long userId;
        if (Type.isEmail(userAccount)) {
            userId = authDao.getIdByEmail(userAccount);
        } else {
            userId = authDao.getIdByPhoneNumber(userAccount);
        }
        return userId;
    }

	@Override
	public Auth saveAndGetToken(long userId) {
		// 创建一个token
	    log.info("Auth getToken userId: "+userId);

        Auth auth;
		String token;
        auth = tokenDao.read(userId);
        if(auth == null){
            token = this.createToken(userId);
            auth = Auth.builder().userId(userId).token(token).build();
            tokenDao.save(auth);
        }
        return auth;
	}

	@Override
	public void detroyAuthRedis(long userId) {
		log.info("Auth detroyAuth userId: "+userId);
		tokenDao.destroyAuth(userId);
		
	}
}
