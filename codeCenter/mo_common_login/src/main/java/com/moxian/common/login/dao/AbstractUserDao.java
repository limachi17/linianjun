package com.moxian.common.login.dao;

import com.moxian.common.login.domain.AbstractUser;

/**
 * Interface for database access to user object
 */
public interface AbstractUserDao {

	/**
	 * 
	 * @Title: findByUserId
	 * @param: long userId
	 * @Description: 通过用户id查询用户信息
	 * @return User
	 */
	AbstractUser findByAbstractUserId(long userId);

	/**
	 * 
	 * @Title: save
	 * @param: User user
	 * @Description: 保存用户信息
	 * @return User
	 */
	AbstractUser createAbstractUser(AbstractUser user);

	/**
	 * 
	 * @Title: update
	 * @param: User user
	 * @Description: 更新用户表信息
	 * @return int
	 */
	int updateAbstractUser(AbstractUser user);

	/**
	 * 
	 * @Title: delete
	 * @param: long userId
	 * @Description: 通过用户id删除用户
	 * @return void
	 */
	void deleteByAbstractUserId(long userId);

	/**
	 * 
	 * @Title: findByPhoneNo
	 * @param: String phoneNo
	 * @param: String countryCode
	 * @Description: TODO()
	 * @return long
	 */
	Long findByPhoneNo(String phoneNo, String countryCode);

	/**
	 * 
	 * @Title: loginUserByEmail
	 * @param: String email
	 * @Description: 通过邮箱登陆(查询用户信息)
	 * @return User
	 */
	AbstractUser loginUserByEmail(String email);

	/**
	 * 
	 * @Title: loginUserByPhone
	 * @param: String phoneNo
	 * @Description: 通过手机号码登陆
	 * @return User
	 */
	AbstractUser loginUserByPhone(String phoneNo);

	/**
	 * 
	* @Title: findUserByPhoneAndCountry 
	* @param: 
	* @Description: 判断是否注册，返回手机号码和国家码
	* @return AbstractUser
	 */
	AbstractUser findUserByPhoneAndCountry(String phoneNo, String countryCode);
}
