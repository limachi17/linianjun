/**
 * @Title: AbstractUserMapper.java
 * @Package com.moxian.com.sso.dao.impl
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年3月31日 下午1:50:53
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.dao.impl;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.moxian.common.login.domain.AbstractUser;

/**
 * @ClassName: AbstractUserMapper
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月31日 下午1:50:53
 *
 */
public interface AbstractUserMapper {

	@Insert("INSERT INTO abstract_user (phone_no, nick_name, password, phone_country_code,moxian_id,create_at,email_verify,sign_source) VALUES (#{phoneNo}, #{nickName}, #{password}, #{phoneCountryCode},#{moxianId},#{createAt},#{emailVerify},#{signSource})")
	@SelectKey(keyProperty = "id", before = false, resultType = long.class, statement = { "SELECT LAST_INSERT_ID() AS id  " })
	void insert(AbstractUser user);

	@Update("UPDATE abstract_user SET phone_no=#{phoneNo}, password=#{password},nick_name=#{nickName}, phone_country_code =#{phoneCountryCode} WHERE id=#{id}")
	@SelectKey(keyProperty = "id", before = false, resultType = long.class, statement = { "SELECT LAST_INSERT_ID() AS id  " })
	int update(AbstractUser user);

	@Delete("DELETE FROM abstract_user WHERE id = #{userId}")
	int delete(long userId);

	@Select("SELECT * FROM abstract_user WHERE id = #{userId}")
	@Results(value = {
			@Result(property = "nickName", column = "nick_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "phoneCountryCode", column = "phone_country_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "phoneNo", column = "phone_no", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "createAt", column = "create_at", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "moxianId", column = "moxian_id", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "emailVerify", column = "email_verify", javaType = boolean.class, jdbcType = JdbcType.BIT) })
	AbstractUser getUser(@Param("userId") Long userId);

	@Select("SELECT id,sign_source FROM abstract_user WHERE phone_no = #{phoneNo} AND phone_country_code = #{countryCode} limit 1")
	@Results(value = {
			@Result(property = "signSource", column = "sign_source", javaType = int.class, jdbcType = JdbcType.INTEGER) })
	AbstractUser getUserRegisterByPhoneNoAndCountryCode(@Param("phoneNo") String phoneNo,
			@Param("countryCode") String countryCode);


	@Select("SELECT id FROM abstract_user WHERE phone_no = #{phoneNo} AND phone_country_code = #{phoneCountryCode}")
	Long checkWhetherUserRegisted(@Param("phoneNo") String phoneNo,
			@Param("phoneCountryCode") String countryCode);

	/**
	 * 通过邮箱账号查询用户信息,必须是老用户
	 * 
	 * @Title: getUserByEmail
	 * @param: String email
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @return User
	 */
	@Select("SELECT * FROM abstract_user WHERE email = #{email}")
	@Results(value = {
			@Result(property = "nickName", column = "nick_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "phoneCountryCode", column = "phone_country_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "phoneNo", column = "phone_no", javaType = String.class, jdbcType = JdbcType.VARCHAR), })
	AbstractUser getUserByEmail(@Param("email") String email);

	/**
	 * 
	 * 
	 * @Title: getUserByPhoneNoAndCountryCode
	 * @param: String phoneNo
	 * @param: String countryCode
	 * @Description: 通过手机账号查询信息
	 * @return User
	 */
	@Select("SELECT * FROM abstract_user WHERE phone_no = #{phoneNo}")
	@Results(value = {
			@Result(property = "nickName", column = "nick_name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "moxianId", column = "moxian_id", javaType = long.class, jdbcType = JdbcType.INTEGER),
			@Result(property = "phoneCountryCode", column = "phone_country_code", javaType = String.class, jdbcType = JdbcType.VARCHAR),
			@Result(property = "phoneNo", column = "phone_no", javaType = String.class, jdbcType = JdbcType.VARCHAR), })
	AbstractUser getUserByPhoneNoAndCountryCode(@Param("phoneNo") String phoneNo);

}
