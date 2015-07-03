/**
 * @Title: MoxianServiceException.java
 * @Package com.moxian.quartz.exception
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月22日 上午11:04:24
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.exception;

import com.moxian.common.exception.BaseException;

/**
 * @ClassName: MoxianServiceException
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月22日 上午11:04:24
 *
 */
public class MoxianServiceException extends BaseException{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = 6650643918323679732L;

	/**
	  *创建一个新的实例 MoxianServiceException
	  * @Title: 
	  * @Description:
	  *
	*/
	public MoxianServiceException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}
	
	
	
}
