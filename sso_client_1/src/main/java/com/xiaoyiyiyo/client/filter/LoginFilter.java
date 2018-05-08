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

        //第一次访问，用户没登录
        //用户拿到token，再次访问，此时is_login依旧为false
        Object is_login = session.getAttribute(AuthConst.IS_LOGIN);
        if (is_login != null && (Boolean) session.getAttribute(AuthConst.IS_LOGIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //第一次访问，token为null
        //用户拿到token，说明已通过验证，将session标记为已登录
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
