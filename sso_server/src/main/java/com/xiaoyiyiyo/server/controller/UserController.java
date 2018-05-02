package com.xiaoyiyiyo.server.controller;

import com.xiaoyiyiyo.server.common.constant.AuthConst;
import com.xiaoyiyiyo.server.common.pojo.UserDo;
import com.xiaoyiyiyo.server.service.IUserService;
import com.xiaoyiyiyo.server.common.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

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

        String userName = map.get("username");
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
            redisTemplate.opsForSet().add(token, clientUrl);
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
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String token = request.getParameter(AuthConst.TOKEN);

        if (StringUtils.isEmpty(token)) {
            token = (String)session.getAttribute(AuthConst.TOKEN);
        }

        Set<String> set = redisTemplate.opsForSet().members(token);
        if (null != set && set.size() > 0) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put(AuthConst.TOKEN, token);
            for (String url: set) {
                HttpUtils.doPost(url, paramMap);
            }
        }
        return "redirect:/";
    }

}