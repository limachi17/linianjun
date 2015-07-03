/**
 * @Title: tt.java
 * @Package com.moxian.common.sso.rest
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月9日 下午5:08:35
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.aop;

/**
 * @ClassName: tt
 * @Description: 输入输出流监控
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月9日 下午5:08:35
 *
 */
import com.google.common.io.ByteStreams;

import lombok.extern.slf4j.Slf4j;
 

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
 
@Slf4j
@Provider
public class RestInterceptor implements ReaderInterceptor, WriterInterceptor {
 
	
	
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws IOException, WebApplicationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteStreams.copy(context.getInputStream(), byteArrayOutputStream);
        log.info("##########ReadFrom InputStream : {}", byteArrayOutputStream.toString());
        context.setInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        return context.proceed();
    }
 
    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        log.info("##########WriteTo Entity : {}", context.getEntity());
        context.proceed();
    }
}