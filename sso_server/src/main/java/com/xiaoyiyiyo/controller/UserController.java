package com.xiaoyiyiyo.controller;

import com.xiaoyiyiyo.pojo.ResultDto;
import com.xiaoyiyiyo.pojo.UserDo;
import com.xiaoyiyiyo.service.IUserService;
import com.xiaoyiyiyo.util.RespUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultDto userLogin(String userName, String password) {

        return RespUtils.success(userService.userLogin(userName, password), System.currentTimeMillis());
    }

    @GetMapping("/logout/{token}")
    public ResultDto logout(@PathVariable String token) {

        userService.logout(token);
        return RespUtils.success(null, System.currentTimeMillis());
    }

    @GetMapping("/token/{token}")
    public ResultDto getUserByToken(@PathVariable String token) {

        return RespUtils.success(userService.getUserByToken(token), System.currentTimeMillis());
    }
}