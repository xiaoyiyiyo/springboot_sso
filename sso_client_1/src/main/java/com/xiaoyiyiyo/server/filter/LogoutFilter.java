package com.xiaoyiyiyo.server.filter;

import com.xiaoyiyiyo.server.common.constant.AuthConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by xiaoyiyiyo on 2018/5/6.
 */
@WebFilter(filterName = "logoutFilter", urlPatterns = "/*")
public class LogoutFilter implements Filter{

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

        if ("/logout".equals(request.getRequestURI())) {
            String token = (String)session.getAttribute(AuthConst.TOKEN);
            //附带系统首页
            response.sendRedirect("http://localhost:8080/logout" + "?" + AuthConst.TOKEN + "=" + token
                + "&" + AuthConst.CLIENT_URL + "=http://localhost:8081/index");
            return;
        }

        String token = request.getParameter(AuthConst.LOGOUT_REUQEST);
        if (!StringUtils.isEmpty(token) && session != null) {
            session.invalidate();
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
