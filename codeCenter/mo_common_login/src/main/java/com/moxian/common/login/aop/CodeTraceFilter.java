/**
 * @Title: tt.java
 * @Package com.moxian.common.sso.rest
 * @Description: 监控请求URI和返回对象
 * Company:moxian
 * 
 * @author Sam
 * @date 2015��4��9�� ����5:10:55
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.aop;
 
/**
 * @ClassName: tt
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015��4��9�� ����5:10:55
 *
 */
import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

import org.glassfish.jersey.server.ContainerRequest;
import org.glassfish.jersey.server.ContainerResponse;
 
@Slf4j
@Provider
public class CodeTraceFilter implements ContainerRequestFilter, ContainerResponseFilter {
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        if (requestContext instanceof ContainerRequest) {
            log.info("##########Request URI : {}, Method : {}",
                ((ContainerRequest) requestContext).getRequestUri().toString(), requestContext.getMethod());
        }
    }
 
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
            throws IOException {
        if (responseContext instanceof ContainerResponse) {
            log.info("##########Response Status : {}, Entity : {}", responseContext.getStatus(),
                    responseContext.getEntity());
        }
    }
 
}