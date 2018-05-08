package com.xiaoyiyiyo.server.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.CookieHttpSessionStrategy;
import org.springframework.session.web.http.DefaultCookieSerializer;

/**
 * Created by xiaoyiyiyo on 2018/4/30.
 */
@Configuration
public class RedisSessionConfig {

    @Bean
    public CookieHttpSessionStrategy cookieHttpSessionStrategy() {
        CookieHttpSessionStrategy strategy=new CookieHttpSessionStrategy();
        DefaultCookieSerializer cookieSerializer=new DefaultCookieSerializer();
        cookieSerializer.setCookieName("SSO_SESSION");//cookies名称
        cookieSerializer.setCookieMaxAge(1800);//过期时间(秒)
        strategy.setCookieSerializer(cookieSerializer);
        return strategy;
    }
}
