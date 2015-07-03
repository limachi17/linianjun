package com.moxian.common.login.dao;

import com.moxian.common.login.domain.Auth;
import com.moxian.common.login.util.Type;

public interface AuthDao {
	
	Long login(long userId, String userPassword);
	
//    String getToken(long userId);
//
//    void updateToken(long userId, String token);

    long getIdByPhoneNumber(String phoneNumber);

    long getIdByEmail(String useraccount);
}
