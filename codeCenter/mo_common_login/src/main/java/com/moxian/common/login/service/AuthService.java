package com.moxian.common.login.service;

import com.moxian.common.login.domain.Auth;

public interface AuthService {
	
	/**
	 * 
	* @Title: isValidTokenPare 
	* @param: 
	* @Description: 验证UserId与token 
	* @return boolean
	 */
    boolean isValidTokenPare(long userId,String token);

    /**
     * 
    * @Title: isValidatePassPare 
    * @param: 
    * @Description: 根据userId与密码，验证登录
    * @return Auth
     */
	Auth isValidatePassPare(long id, String userpass);

	/** 
	 * 
	 * @Title: getToken
	 * @param:
	 * @Description: 给指定用户创建token
	 * @return Auth
	 */
	Auth getToken(long userId);
	
	
	/**
	 * 
	* @Title: getUserIdByAccount 
	* @param: 
	* @Description: 根据手机号码或者邮箱，获取登录用户的UserId
	* @return long
	 */
	long getUserIdByAccount(String userAccount);
	
	
	/**
	 * 
	* @Title: saveAndGetToken 
	* @param: 
	* @Description: 传入userId生成token
	* @return Auth
	 */
	Auth saveAndGetToken(long userId);
	
	/**
	 * 
	* @Title: detroyAuthRedis 
	* @param: 
	* @Description: 传入userId清除redis中的token，即踢下线 
	* @return void
	 */
	void detroyAuthRedis(long userId);
	
	
}
