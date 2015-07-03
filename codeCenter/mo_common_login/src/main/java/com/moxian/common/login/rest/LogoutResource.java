/**
 * @Title: LogoutResource.java
 * @Package com.moxian.common.login.rest
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年6月3日 下午5:42:40
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.rest;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import com.moxian.common.bo.BoUtil;
import com.moxian.common.exception.MoxianRestException;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.util.ErrorCode;
import com.moxian.common.rest.RestBase;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
 * @ClassName: LogoutResource
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年6月3日 下午5:42:40
 *
 */
@Slf4j
@Path(LogoutResource.BASE_PATH)
@Api(value = LogoutResource.BASE_PATH, description = "Logout API")
public class LogoutResource extends RestBase{
	
	public static final String BASE_PATH = "logout";
	
	private final AuthService authService;

	@Inject
	public LogoutResource(final @NonNull AuthService authService) {
		this.authService = authService;
	}

	
	
	@POST
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("user logout")
	public BoUtil userLogout(){
		long loginUserId = super.getLoginUserID();
		if(loginUserId ==0 ){
			throw new MoxianRestException(ErrorCode.USERID_CANNOT_BE_NULL, "userId cannot be load");
		}
		authService.detroyAuthRedis(loginUserId);
		BoUtil bo = BoUtil.getDefaultTrueBo();
		bo.setMsg("user logout Success!");
		return bo;
	}

}
