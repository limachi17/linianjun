package com.moxian.common.login.dao.impl;

import com.moxian.common.login.dao.AuthDao;
import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.util.HashGenerator;

public class AuthDaoMyBatisImpl implements AuthDao {

	final AuthMapper authMapper;

	public AuthDaoMyBatisImpl(AuthMapper authMapper) {
		this.authMapper = authMapper;
	}

	@Override
	public Long login(long userId, String userPassword) {
		Long userIdInDb = authMapper.login(userId, userPassword);
/*
		if (userIdInDb != null) {
			token = HashGenerator.generateToken(String.valueOf(userId));
			//authMapper.updateToken(userId, token);
			auth.setUserId(userId);
			auth.setToken(token);
		}*/
		return userIdInDb;
	}

//	@Override
//	public String getToken(long userId) {
//		return authMapper.getToken(userId);
//	}
//
//	@Override
//	public void updateToken(long userId, String token) {
//		authMapper.updateToken(userId, token);
//	}

	@Override
	public long getIdByPhoneNumber(String phoneNumber) {
		try {
			return Long.parseLong(authMapper.getIdByPhoneNumber(phoneNumber));
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	@Override
	public long getIdByEmail(String email) {
		try {
		    String userId = authMapper.getIdByEmail(email);
			return Long.parseLong(userId);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
}
