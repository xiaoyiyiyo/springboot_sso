package com.xiaoyiyiyo.server.service;

import com.xiaoyiyiyo.server.common.pojo.UserDo;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public interface IUserService {

    UserDo getUser(String userName, String password);
}
