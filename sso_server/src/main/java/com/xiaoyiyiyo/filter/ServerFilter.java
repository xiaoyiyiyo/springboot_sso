package com.xiaoyiyiyo.filter;

import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiaoyiyiyo on 2018/4/24.
 */
@WebFilter
public class ServerFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)servletRequest;
        HttpServletResponse res = (HttpServletResponse)servletResponse;

        String userName = "";
        String serviceUrl = req.getParameter("service_url");

        //获取用户名
        Cookie[] cookies = req.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("sso".equals(cookie.getName())) {
                    userName = cookie.getValue();
                    break;
                }
            }
        }

        if (StringUtils.isEmpty(serviceUrl) || StringUtils.isEmpty(userName)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.sendRedirect(serviceUrl);
        }
    }

    @Override
    public void destroy() {

    }
}
