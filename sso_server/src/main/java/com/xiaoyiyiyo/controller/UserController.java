package com.xiaoyiyiyo.controller;

import com.xiaoyiyiyo.common.AuthConst;
import com.xiaoyiyiyo.pojo.UserDo;
import com.xiaoyiyiyo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody Map<String, String> map) throws IOException {

        String userName = map.get("userName");
        String password = map.get("password");
        String clientUrl = map.get("clientUrl");

        UserDo user = userService.getUser(userName, password);

        if (user == null) {
            return "redirect:/";
        }

        //设置全局session，并缓存
        HttpSession session = request.getSession();
        String token = UUID.randomUUID().toString();
        session.setAttribute(AuthConst.IS_LOGIN, true);
        session.setAttribute(AuthConst.TOKEN, token);

        if (!StringUtils.isEmpty(clientUrl)) {
            redisTemplate.opsForValue().set(AuthConst.CACHE_CLIENT_URL + token, clientUrl);
            if (clientUrl.contains("?")) {
                clientUrl = clientUrl + "&";
            } else {
                clientUrl = clientUrl + "?";
            }
            return "redirect: " + clientUrl + AuthConst.TOKEN + "=" + token;
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = request.getParameter(AuthConst.TOKEN);

        if (!StringUtils.isEmpty(token)) {

        }
    }

}