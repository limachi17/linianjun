/**
 * @Title: ErrorCode.java
 * @Package com.moxian.com.sso.util
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年3月30日 下午5:48:29
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.util;

/**
 * @ClassName: ErrorCode
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月30日 下午5:48:29
 *
 */
public class ErrorCode {

	/**
	 * 成功
	 */
	public final static String SUCCESS = "mx0000000";

	/**
	 * 手机号码格式错误
	 */
	public final static String PHONE_FORMAT_ERROR = "mx1412001";

	/**
	 * 密码长度异常
	 */
	public final static String ACCOUNT_PWD_LENGTH_ERROR = "mx1412002";

	/**
	 * 号码已被注册
	 */
	public final static String PHONE_REGISTED_ERROR = "mx1412003";

	/**
	 * 发送验证码错误
	 */
	public final static String SEND_REG_CAPTHCA_ERROR = "mx1412004";

	/**
	 * 一分钟内发送
	 */
	public final static String SEND_ONE_MINUTE_LIMIT_ERROR = "mx1412005";

	/**
	 * 每天发送限制
	 */
	public final static String SEND_DAY_LIMIT_ERROR = "mx1412006";

	/**
	 * 验证码不匹配
	 */
	public final static String CAPTCHA_NOT_MATCH_ERROR = "mx1412007";

	/**
	 * 手机号码或国家码不能为空
	 */
	public final static String PHONE_EMPTY_ERROR = "mx1412008";

	/**
	 * 未找到操作对象
	 */
	public final static String OPERATE_OBJECT_NO_FOUND = "mx1412009";

	/**
	 * 验证token失败
	 */
	public final static String AUTH_TOKEN_FAIL = "mx1412010";
	/**
	 * 账号或密码错误
	 */
	public final static String LOGIN_FAIL = "mx1412011";

	/**
	 * 手机账号不存在
	 */
	public final static String PHONE_ACCOUNT_NOT_EXIST = "mx1412012";

	/**
	 * 邮箱格式错误
	 */
	public final static String EMAIL_FORMAT_ERROR = "mx1412013";

	/**
	 * 邮箱账号不存在
	 */
	public final static String EMAIL_ACCOUNT_NOT_EXIST = "mx1412014";

	/**
	 * 参数错误
	 */
	public final static String PARAM_ERROR = "mx1412015";

	/**
	 * 新密码不能为空
	 */
	public final static String NEWPASSWORD_CANNOT_BE_NULL = "mx1412016";

	/**
	 * 该用户不存在
	 */
	public final static String USER_IS_NOT_EXIST = "mx1412017";
	
	/**
	 * 用户id不能为0或者空
	 */
	public final static String USERID_CANNOT_BE_NULL = "mx1412018";
	
	/**
	 * token不能为空
	 */
	public final static String TOKEN_CANNOT_BE_NULL = "mx1412019";

	/**
	 * 账号或密码不能为空
	 */
	public final static String ACCOUNT_OR_PASS_CANNOT_BE_NULL = "mx1412020";
	
	/**
	 * 账号不存在，未注册
	 */
	public final static String ACCOUNT_IS_NOT_EXIST = "mx1412021";

	/**
	 * 作为魔线的用户，你可以直接使用魔线账号登录哦~
	 */
	public final static String MOXIAN_REGISTED_ERROR = "mx1412022";

	/**
	 * 您已经拥有魔商账号，可以直接登录哦~
	 */
	public final static String MOBIZ_REGISTED_ERROR = "mx1412023";
}
