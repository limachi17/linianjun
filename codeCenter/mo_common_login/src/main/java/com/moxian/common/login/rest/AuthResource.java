package com.moxian.common.login.rest;

import java.sql.Timestamp;

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
import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.util.ErrorCode;
import com.moxian.common.login.vo.ValidatePassVo;
import com.moxian.common.login.vo.ValidateTokenVo;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

@Slf4j
@Path(AuthResource.BASE_PATH)
@Api(value = AuthResource.BASE_PATH, description = "Auth API")
public class AuthResource {

	public static final String BASE_PATH = "auth";
	private static final String NOT_FOUND_FORMAT = "Auth with ID '%s' not found";
	private static final String INVALID_LOGIN_REQUEST = "Username / password is not correct";

	private final AuthService authService;

	@Inject
	public AuthResource(final @NonNull AuthService authService) {
		this.authService = authService;
	}

	@POST
	@Path("/valid")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Validate a request")
	public BoUtil validateToken(
			final @NotNull @Valid ValidateTokenVo validateTokenVo) {
		long userId = validateTokenVo.getUserId();
		String token = validateTokenVo.getToken();
		if (userId == 0){
			throw new MoxianRestException(ErrorCode.USERID_CANNOT_BE_NULL, "userId cannot be null");
		}
		if(token.trim() == "" ||token==null){
			throw new MoxianRestException(ErrorCode.USERID_CANNOT_BE_NULL, "token cannot be null");
		}
		boolean isValid = authService.isValidTokenPare(userId, token);
		BoUtil bu = BoUtil.getDefaultFalseBo();
		bu.setResult(isValid);
		if (isValid) {
			bu.setCode(ErrorCode.SUCCESS);
			bu.setMsg("token validate success");
		} else {
			bu.setCode(ErrorCode.AUTH_TOKEN_FAIL);
			bu.setMsg("token validate fail");
		}
		return bu;
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation("Validate a request")
	public BoUtil validatePass(
			final @NotNull @Valid ValidatePassVo validatePassVo) {
		String userAccount = validatePassVo.getUseraccount();
		String userPass = validatePassVo.getUserpass();
		if (userAccount==null || userPass==null || userAccount.trim() == "" || userPass.trim() == ""){
			throw new MoxianRestException(ErrorCode.ACCOUNT_OR_PASS_CANNOT_BE_NULL, "user account or password cannot be null");
		}
		long userId = authService.getUserIdByAccount(userAccount);
		if(userId == 0){
			throw new MoxianRestException(ErrorCode.ACCOUNT_IS_NOT_EXIST, "this phone or email is not registed");
		}
		Auth auth = authService.isValidatePassPare(userId, userPass);
		if (auth == null) {
			throw new MoxianRestException(ErrorCode.LOGIN_FAIL, "user account or password not match");
		}
		
		BoUtil bo = BoUtil.getDefaultTrueBo();
		bo.setMsg("login Success!");
		bo.setData(auth);
		return bo;
	}
	


}