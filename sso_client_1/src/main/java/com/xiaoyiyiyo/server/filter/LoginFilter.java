package com.xiaoyiyiyo.server.filter;

import com.xiaoyiyiyo.server.common.constant.AuthConst;

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

        if ((Boolean) session.getAttribute(AuthConst.IS_LOGIN)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String token = request.getParameter(AuthConst.TOKEN);
        if (token != null) {
            session.setAttribute(AuthConst.TOKEN, token);
            session.setAttribute(AuthConst.IS_LOGIN, true);
        }
        response.sendRedirect("");
    }

    @Override
    public void destroy() {

    }
}
