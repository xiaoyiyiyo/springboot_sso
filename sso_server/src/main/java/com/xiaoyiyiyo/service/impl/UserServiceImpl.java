package com.xiaoyiyiyo.service.impl;

import com.xiaoyiyiyo.pojo.UserDo;
import com.xiaoyiyiyo.repository.IRedisClient;
import com.xiaoyiyiyo.repository.IUserRepository;
import com.xiaoyiyiyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    private IRedisClient redisClient;

    @Override
    public UserDo userLogin(String userName, String password) {

        //查询User
        UserDo user = userRepository.findByAccount(userName);

        String token = UUID.randomUUID().toString();
        //清理密码，之后恢复
        String pw = user.getPassword();
        String salt = user.getSalt();
        user.setPassword(null);
        user.setSalt(null);
        //存入缓存
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
