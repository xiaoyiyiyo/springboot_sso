package com.xiaoyiyiyo.server.filter;

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
 * Created by xiaoyiyiyo on 2018/5/1.
 */
@WebFilter(filterName = "sessionFilter", urlPatterns = {"/login", "/logout"})
public class SessionFilter implements Filter{

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        HttpSession session  = request.getSession();

        String uri = request.getRequestURI();

        //注销请求，放行
        if ("/logout".equals(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //已经登录， 放行
        if (session.getAttribute(AuthConst.IS_LOGIN) != null) {
            String clientUrl = request.getParameter(AuthConst.CLIENT_URL);
            String token = (String) session.getAttribute(AuthConst.TOKEN);
            if (clientUrl != null && !"".equals(clientUrl)) {
                redisTemplate.opsForSet().add(token, clientUrl);
                if (clientUrl.contains("?")) {
                    clientUrl = clientUrl + "&";
                } else {
                    clientUrl = clientUrl + "?";
                }
                response.sendRedirect(clientUrl + AuthConst.TOKEN + "=" + token);
                return;
            }
//            if ("/success".equals(uri)) {
//                response.sendRedirect("/success");
//                return;
//            }
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

//        String token = request.getParameter(AuthConst.TOKEN);
//        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(redisTemplate.opsForValue().get(token))) {
//
//        }

        //登录请求，放行
        if ("/".equals(uri) || "/login".equals(uri)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //其他请求
        response.sendRedirect("/");
    }

    @Override
    public void destroy() {

    }
}
