/**
 * @Title: RegistVo.java
 * @Package com.moxian.com.sso.vo
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年3月31日 上午11:58:19
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @ClassName: RegistVo
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月31日 上午11:58:19
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegistVo {
	
	
	/**
	 * 手机号码国家码
	 */
	private String countryCode;
	
	/**
	 * 手机号码
	 */
	private String phoneNo;
		
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 验证码
	 */
	private String captcha;

	/**
	 * 昵称
	 */
	private String nickName;

	/**
	 * 注册来源  1 魔线 2 魔商
	 */
	private int signSource;



}
