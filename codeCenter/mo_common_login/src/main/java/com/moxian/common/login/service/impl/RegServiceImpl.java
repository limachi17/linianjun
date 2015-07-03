package com.moxian.common.login.service.impl;

import javax.inject.Inject;

import org.springframework.cache.annotation.CachePut;
import org.springframework.transaction.annotation.Transactional;

import com.moxian.common.login.dao.AbstractUserDao;
import com.moxian.common.login.domain.AbstractUser;
import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.service.AuthService;
import com.moxian.common.login.service.RegService;

/**
 * @ClassName: RegServiceImpl
 * @Description: 注册service
 * @author Sam sam.liang@moxiangroup.com
 * @Company moxian
 * @date 2015年3月31日 下午2:15:26
 *
 */
public class RegServiceImpl implements RegService {

	@Inject
	private AbstractUserDao absUserDao;

	@Inject
	private AuthService authService;


	@Override
	@Transactional
	public Auth createAbstractUser(AbstractUser user) {
		absUserDao.createAbstractUser(user);
		return authService.getToken(user.getId());
	}

	@Override
	public AbstractUser findByAbstractUserId(long userId) {
		return absUserDao.findByAbstractUserId(userId);
	}

	@Override
	public void updateAbstractUser(AbstractUser user) {
		absUserDao.updateAbstractUser(user);
	}

	@Override
	public Long hasRegByPhoneNo(String phoneNo, String countryCode) {
		return absUserDao.findByPhoneNo(phoneNo, countryCode);
	}

	@Override
	public AbstractUser hasRegisterByPhoneNo(String phoneNo, String countryCode) {
		return absUserDao.findUserByPhoneAndCountry(phoneNo, countryCode);
	}

}
