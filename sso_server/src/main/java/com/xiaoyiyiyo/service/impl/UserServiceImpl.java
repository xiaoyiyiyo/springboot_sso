package com.xiaoyiyiyo.service.impl;

import com.xiaoyiyiyo.pojo.UserDo;
import com.xiaoyiyiyo.service.IUserService;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public class UserServiceImpl implements IUserService {

    @Override
    public UserDo userLogin(String userName, String password) {
        return null;
    }

    @Override
    public void logout(String token) {

    }

    @Override
    public UserDo getUserByToken(String token) {
        return null;
    }
}
