package com.xiaoyiyiyo.service;

import com.xiaoyiyiyo.pojo.UserDo;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public interface IUserService {

    UserDo userLogin(String userName, String password);

    void logout(String token);

    UserDo getUserByToken(String token);
}
