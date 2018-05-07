package com.xiaoyiyiyo.client.filter;

import com.xiaoyiyiyo.server.common.constant.AuthConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by xiaoyiyiyo on 2018/5/2.
 */
@WebFilter(filterName = "loginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session = request.getSession();

        if ("/index".equals(request.getRequestURI())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //当前浏览器与系统之间的session是否已登录
        Object is_login = session.getAttribute(AuthConst.IS_LOGIN);
        if (is_login != null && (Boolean) session.getAttribute(AuthConst.IS_LOGIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //没有登录的时候request中没有token，需将用户请求重定向到认证中心，然后认证中心生成token中重定向到系统
        //当前系统拿到token
        String token = request.getParameter(AuthConst.TOKEN);
        if (token != null) {
            session.setAttribute(AuthConst.TOKEN, token);
            session.setAttribute(AuthConst.IS_LOGIN, true);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //没有登录将用户请求重定向到认证中心
        response.sendRedirect("http://localhost:8080/login" + "?" + AuthConst.CLIENT_URL + "=" + request.getRequestURL());
    }

    @Override
    public void destroy() {

    }
}
