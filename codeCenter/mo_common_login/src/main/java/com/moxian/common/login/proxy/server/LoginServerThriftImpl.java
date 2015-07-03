/**
 * @Title: SSOServerThriftImpl.java
 * @Package com.moxian.com.sso.proxy
 * @Description: TODO
 * Company:moxian
 * 
 * @author Sam
 * @date 2015年4月1日 下午3:54:35
 * @version Moxian M1 V1.0
 */
package com.moxian.common.login.proxy.server;

import javax.inject.Inject;

import org.apache.thrift.TException;

import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.proxy.model.AuthModel;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.service.RegService;

/**
 * @ClassName: SSOServerThriftImpl
 * @Description: TODO
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年4月1日 下午3:54:35
 *
 */
public class LoginServerThriftImpl implements LoginServerThrift.Iface {

	@Inject
	private RegService regService;

	@Inject
	private AuthService authService;

	@Override
	public boolean validateToken(long userId, String token) throws TException {
		return authService.isValidTokenPare(userId, token);
	}

	@Override
	public long whetherRegisted(String phoneCountryCode, String phoneNo)
			throws TException {
		Long userId = regService.hasRegByPhoneNo(phoneNo, phoneCountryCode);
		if (userId == null) {
			return 0;
		}
		return userId;
	}

	@Override
	public AuthModel saveAndGetToken(long userId) {
		AuthModel authMode = null;
		Auth auth = authService.saveAndGetToken(userId);
		if(auth != null){
			authMode = new AuthModel();
			authMode.setToken(auth.getToken());
			authMode.setUserId(auth.getUserId());
		}
		return authMode;
	}
	
	public boolean destoryAuthByUserId(long userId){
		authService.detroyAuthRedis(userId);
		return true;
	}




}
