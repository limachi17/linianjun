/**
 * @Title: BaseException.java
 * @Package com.moxian.quartz.exception
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月23日 下午5:49:44
 * @version Moxian M1 V1.0
 */
package com.moxian.quartz.exception;

/**
 * @ClassName: BaseException
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月23日 下午5:49:44
 *
 */
public class BaseException extends RuntimeException{
	 /** 
     * the serialVersionUID 
     */  
    private static final long serialVersionUID = 1381325479896057076L;  
  
    /** 
     * 错误码
     */  
    private String errorCode;  

    
    /**
     * 错误信息
     */
    private String errorMsg;
    
    
    /** 
     * message params 
     */  
    private Object[] values;  
    
    
    
    
    
    /**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMsg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * @param errorMsg the errorMsg to set
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	/** 
     * @return the values 
     */  
    public Object[] getValues() {  
        return values;  
    }  
  
    /** 
     * @param values the values to set 
     */  
    public void setValues(Object[] values) {  
        this.values = values;  
    }

	/**
	  *创建一个新的实例 BaseException
	  * @Title: 
	  * @Description:
	  *
	*/
	public BaseException(String errorCode, String errorMsg, Object[] values) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.values = values;
	}  
	
	/**
	  *创建一个新的实例 BaseException
	  * @Title: 
	  * @Description:
	  *
	*/
	public BaseException(String errorCode, String errorMsg) {
		//super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	/**
	  *创建一个新的实例 BaseException
	  * @Title: 
	  * @Description:
	  *
	*/
	public BaseException(Throwable cause, String errorCode, String errorMsg,
			Object[] values) {
		//this.cause = null;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.values = values;
	}
  

    
/*    public BaseException(String message,String code) {  
        this.code = code;  
        this.message = message;  
    } */ 
}
