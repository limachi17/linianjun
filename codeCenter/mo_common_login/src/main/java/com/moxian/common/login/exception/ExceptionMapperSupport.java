/**
 * @Title: ExceptionMapperSupport.java
 * @Package com.moxian.demo.exception
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月23日 下午5:44:04
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.exception;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.moxian.common.bo.BoUtil;
import com.moxian.common.exception.BaseException;

/**
 * @ClassName: ExceptionMapperSupport
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月23日 下午5:44:04
 *
 */
@Slf4j
@Provider
public class ExceptionMapperSupport implements ExceptionMapper<Exception> {

	private static final String CONTEXT_ATTRIBUTE = WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE;  
	  
    @Context  
    private HttpServletRequest request;  
  
    @Context  
    private ServletContext servletContext;  
  
    /** 
     * 异常处理 
     *  
     * @param exception 
     * @return 异常处理后的Response对象 
     */  
    public Response toResponse(Exception exception) {  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//定义格式，不显示毫秒 
		Timestamp now = new Timestamp(System.currentTimeMillis());
		//获取系统当前时间 
		String time = df.format(now);
		
    	log.info("########### ExceptionMapperSupport Start");
        String msg = "";  
        String code = "";
        Status statusCode = Status.ACCEPTED;  
        if (exception instanceof BaseException) {  
            BaseException baseException = (BaseException) exception;  
            code = baseException.getErrorCode();
            msg = baseException.getErrorMsg();
        } 
        else if (exception instanceof NotFoundException) {  
        	msg = "NotFoundException";  
            statusCode = Status.NOT_FOUND;  
        }
        else if(exception instanceof JsonParseException) {
        	msg = "JsonParseException";
        	statusCode = Status.BAD_REQUEST;
        }
        else {  
        	msg = "Internal Server Error";  
        	statusCode = Status.INTERNAL_SERVER_ERROR;
        }   
        // checked exception和unchecked exception均被记录在日志里  
        log.error(msg, exception); 
        log.info("########### ExceptionMapperSupport End");
        BoUtil bo = BoUtil.builder().code(code).msg(msg).date(time).result(false).build();
        return Response.ok(bo, MediaType.APPLICATION_JSON).status(statusCode)  
                .build();  
    }  
}
