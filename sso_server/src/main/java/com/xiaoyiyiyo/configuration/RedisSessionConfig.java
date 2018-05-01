package com.xiaoyiyiyo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * Created by xiaoyiyiyo on 2018/4/30.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3000)
public class RedisSessionConfig {

}
