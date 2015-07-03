package com.moxian.common.login.dao.impl;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.type.JdbcType;

import com.moxian.common.login.domain.Auth;

public interface AuthMapper {

    // TODO: 登陆并获取用户信息 boolean要换成详细信息
    @Select("select user_user_base_sb_seq from user_user_base_sb where user_user_base_sb_seq=#{userId} and user_base_password=#{userPassword}")
    @Results(value={
            @Result(property="userId", column="user_user_base_sb_seq", javaType=long.class,jdbcType=JdbcType.BIGINT)
        })
    Long login(@Param("userId") long userId,@Param("userPassword") String userPassword);
    
//    @Insert("INSERT INTO auth(token, user_id) VALUES(#{token}, #{userId}) ON DUPLICATE KEY UPDATE token=#{token}")
//    void updateToken(@Param("userId") long userId,@Param("token") String token);
//    
//    @Select("select token from auth where user_id=#{userId}")
//    String getToken(@Param("userId") long userId);

    @Select("select user_user_base_sb_seq from user_user_base_sb where user_base_phone=#{phoneNumber}")
    String getIdByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    @Select("select user_user_base_sb_seq from user_user_base_sb where user_base_email=#{email}")
    String getIdByEmail(@Param("email") String email);
}
