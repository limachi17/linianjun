package com.moxian.common.login.dao.impl;

import com.moxian.common.login.dao.AbstractUserDao;
import com.moxian.common.login.domain.AbstractUser;

/**
 * Implementation of UserDao interface using MyBatis
 */
public class AbstractUserDaoMyBatisImpl implements AbstractUserDao {

	final private AbstractUserMapper absUserMapper;

	public AbstractUserDaoMyBatisImpl(AbstractUserMapper absUserMapper) {
		this.absUserMapper = absUserMapper;
	}

	@Override
	public AbstractUser findByAbstractUserId(long userId) {
		return absUserMapper.getUser(userId);
	}

	@Override
	public AbstractUser createAbstractUser(AbstractUser user) {
		absUserMapper.insert(user);
		return user;
	}

	@Override
	public int updateAbstractUser(AbstractUser user) {
		return absUserMapper.update(user);
	}

	@Override
	public void deleteByAbstractUserId(long userId) {
		absUserMapper.delete(userId);
	}

	@Override
	public Long findByPhoneNo(String phoneNo, String countryCode) {
		return absUserMapper.checkWhetherUserRegisted(phoneNo,
				countryCode);
	}

	

	

	@Override
	public AbstractUser loginUserByEmail(String email) {
		return absUserMapper.getUserByEmail(email);
	}

	@Override
	public AbstractUser loginUserByPhone(String phoneNo) {
		return absUserMapper.getUserByPhoneNoAndCountryCode(phoneNo);
	}

	@Override
	public AbstractUser findUserByPhoneAndCountry(String phoneNo,
			String countryCode) {
		return absUserMapper.getUserRegisterByPhoneNoAndCountryCode(phoneNo, countryCode);
	}


}
