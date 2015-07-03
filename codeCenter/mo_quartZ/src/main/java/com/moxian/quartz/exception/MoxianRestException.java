/**
 * @Title: MoxianRestException.java
 * @Package com.moxian.quartz.exception
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月24日 下午12:18:43
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.exception;

import com.moxian.common.exception.BaseException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Builder;

/**
 * @ClassName: MoxianRestException
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月24日 下午12:18:43
 *
 */

public class MoxianRestException extends BaseException{
	
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	private static final long serialVersionUID = -1121964273059589538L;



	/**
	  *创建一个新的实例 MoxianRestException
	  * @Title: 
	  * @Description:
	  *
	*/
	public MoxianRestException(String errorCode, String errorMsg) {
		super(errorCode, errorMsg);
	}


    
    


}
