package com.moxian.common.login.domain;
 
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;
 

import javax.persistence.*;
 
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AbstractUser implements Serializable{
 
	/**
	 * 系统生成ID
	 */
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   
   /**
    * 业务显示ID
    */
   @Column(nullable = true)
   private long moxianId;
 
   /**
    * 用户昵称
    */
   @Column(nullable = true)
   private String nickName;
   
   /**
    * 邮箱
    */
   @Column(nullable = true, unique=true)
   private String email;
 
   /**
    * 密码
    */
   @Column(nullable = false)
   private String password;
 
   /**
    * 手机号码
    */
   @Column(nullable = false, unique=true)
   private String phoneNo;
   
   /**
    * 创建时间
    */
   @Column(nullable = true)
   private long createAt;
   
   
   /**
    * 是否经过邮箱验证
    */
   @Column(nullable = true)
   private boolean emailVerify;
   
   /**
    * 手机号国家码
    */
   @Column(nullable = true)
   private String phoneCountryCode;

   /**
    * 注册来源
    */
   @Column(nullable = true)
   private int signSource;
 

 
}