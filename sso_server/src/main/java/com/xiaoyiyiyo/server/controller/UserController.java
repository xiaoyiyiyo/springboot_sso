package com.xiaoyiyiyo.server.controller;

import com.xiaoyiyiyo.server.common.constant.AuthConst;
import com.xiaoyiyiyo.server.common.pojo.UserDo;
import com.xiaoyiyiyo.server.service.IUserService;
import com.xiaoyiyiyo.server.common.util.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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


    @GetMapping("/login")
    public String login(HttpServletRequest request, ModelMap modelMap) {
        modelMap.addAttribute("clientUrl", request.getParameter(AuthConst.CLIENT_URL));
        return "index";
    }


    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response,
                        @RequestParam Map<String, String> map) throws IOException {

        String userName = map.get("username");
        String password = map.get("password");
        String clientUrl = map.get("clientUrl");

        UserDo user = userService.getUser(userName, password);

        if (user == null) {
            response.sendRedirect("/");
            return;
        }

        //设置全局session，并缓存
        HttpSession session = request.getSession();
        String token = UUID.randomUUID().toString();
        session.setAttribute(AuthConst.IS_LOGIN, true);
        session.setAttribute(AuthConst.TOKEN, token);

        //根据token, 缓存用户
        redisTemplate.opsForValue().set("user:" + token, user);

        if (!StringUtils.isEmpty(clientUrl)) {

            // 缓存各个系统的地址
            redisTemplate.opsForSet().add(AuthConst.CLIENT_URL + ":"+ token, clientUrl);

            if (clientUrl.contains("?")) {
                clientUrl = clientUrl + "&";
            } else {
                clientUrl = clientUrl + "?";
            }
            response.sendRedirect(clientUrl + AuthConst.TOKEN + "=" + token);
            return;
        }
        response.sendRedirect("/");
        return;
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String token = request.getParameter(AuthConst.TOKEN);
        String clientUrl = request.getParameter(AuthConst.CLIENT_URL);

        // 当request参数中token为空，可从session中获取
        if (StringUtils.isEmpty(token)) {
            token = (String)session.getAttribute(AuthConst.TOKEN);
        }

        //销毁session
        if (session != null) {
            session.invalidate();
        }

        //通知各个系统注销
        Set<String> set = redisTemplate.opsForSet().members(AuthConst.CLIENT_URL + ":" + token);
        if (null != set && set.size() > 0) {
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put(AuthConst.LOGOUT_REUQEST, token);
            for (String url: set) {
                HttpUtils.doPost(url, paramMap);
            }
        }

        if (StringUtils.isEmpty(clientUrl)) {
            response.sendRedirect("/");
        }
        response.sendRedirect(clientUrl);
    }

}