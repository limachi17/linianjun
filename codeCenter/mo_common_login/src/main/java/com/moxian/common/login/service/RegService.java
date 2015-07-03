package com.moxian.common.login.service;

import com.moxian.common.login.domain.AbstractUser;
import com.moxian.common.login.domain.Auth;


/**
 * 
  * @ClassName: RegService
  * @Description: 注册serivice
  * @author Sam sam.liang@moxiangroup.com
  * @Company moxian
  * @date 2015年3月14日 下午4:17:15
  *
 */
public interface RegService {

	/**
	 * 
	* @Title: createAbstractUser 
	* @param: UserRegVo reg
	* @Description: 注册创建基本用户
	* @return Auth
	 */
	public Auth createAbstractUser(AbstractUser reg);

	/**
	 * 
	* @Title: findByUserId 
	* @param: long userId
	* @Description: 通过用户id查询用户信息
	* @return User
	 */
	public AbstractUser findByAbstractUserId(long userId);

	/**
	 * 
	* @Title: update 
	* @param: User reg
	* @Description: 更新用户信息
	* @return void
	 */
	public void updateAbstractUser(AbstractUser reg);

	/**
	* @Title: hasRegByPhoneNo 
	* @param: String phoneNo
	* @param: String countryCode
	* @Description: 通过手机号查询用户是否注册
	* @return long
	 */
	public Long hasRegByPhoneNo(String phoneNo, String countryCode);

	/**
	* @Title: hasRegByPhoneNo 
	* @param: String phoneNo
	* @param: String countryCode
	* @Description: 通过手机号查询用户是否注册
	* @return long
	 */
	public AbstractUser hasRegisterByPhoneNo(String phoneNo, String countryCode);

}
