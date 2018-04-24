package com.xiaoyiyiyo.servlet;

import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
@WebServlet(name = "loginServlet", urlPatterns = "/user/login")
public class LoginServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // 获取请求信息，e.g. 用户名，密码
        String userName = req.getParameter("user_name");
        String password = req.getParameter("password");
        String serviceUrl = req.getParameter("service_url");

        //简单做下登录验证
        if ("test".equals(userName) && "test".equals(password)) {
            Cookie cookie = new Cookie("sso", userName);
            cookie.setPath("/");
            cookie.setMaxAge(60);
            res.addCookie(cookie);

            // 如果serviceUrl为空，则跳转到登录页面，如果不为空，跳转到系统地址
            res.sendRedirect(StringUtils.isEmpty(serviceUrl) ? "/index.html" : serviceUrl);
        } else {
            res.sendRedirect("/index.html?service_url=" + serviceUrl);
        }
    }
}
