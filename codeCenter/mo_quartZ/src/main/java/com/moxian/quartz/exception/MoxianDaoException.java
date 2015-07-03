/**
 * @Title: MoxianDaoException.java
 * @Package com.moxian.quartz.exception
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月22日 上午11:04:05
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.exception;

import com.moxian.common.exception.BaseException;

/**
 * @ClassName: MoxianDaoException
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月22日 上午11:04:05
 *
 */
public class MoxianDaoException extends BaseException{
	
    /**
	  *创建一个新的实例 MoxianDaoException
	  * @Title: 
	  * @Description:
	  *
	*/
	public MoxianDaoException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}

	private static final long serialVersionUID = -3711290613973933714L;  
    
    

}
