package com.moxian.common.login.dao;

import com.moxian.common.login.domain.Auth;

public interface TokenRedisDao {

    public void save(final Auth auth);

    public Auth read(final long userId);
    
    public void destroyAuth(final long userId);

}