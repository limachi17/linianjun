package com.moxian.common.login;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import lombok.extern.slf4j.Slf4j;

import org.glassfish.jersey.server.ContainerResponse;

import com.moxian.common.bo.BoUtil;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.util.ErrorCode;
import com.moxian.common.util.BaseUtil;

@Slf4j
@Provider
public class RestFilter implements ContainerRequestFilter,
		ContainerResponseFilter {

/*	@Inject
	private ThriftClientLoginConfig thriftClientLoginConfig;*/
	@Inject
	private AuthService authService;
	
	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {
		String requestUri = requestContext.getUriInfo().getPath();
		boolean bool = false;
		String id = requestContext.getHeaderString("userId");
		String token = requestContext.getHeaderString("token");
		
		long userId = 0;
		int type = 0;
		if (id != null && token != null) {
			userId = Long.parseLong(id == null ? "0" : id);
			
			bool = authService.isValidTokenPare(userId, token);
		}
		// 过滤掉不需要验证的模块
		if (requestUri.indexOf("api-docs") >= 0) {
			bool = true;
		}
		if (requestUri.indexOf("auth") >= 0) {
				bool = true;
		}
		if (!bool) {
			BoUtil boUtil = BoUtil.getDefaultFalseBo();
			boUtil.setCode(ErrorCode.AUTH_TOKEN_FAIL);
			requestContext.abortWith(Response
					.status(Response.Status.UNAUTHORIZED).entity(boUtil)
					.build());
		}else {
			requestContext.getHeaders().add(BaseUtil.LOGIN_USERID, userId + "");
		}

	}

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		if (responseContext instanceof ContainerResponse) {
			log.info("Response Status : {}, Entity : {}",
					responseContext.getStatus(), responseContext.getEntity());

		}
	}

}