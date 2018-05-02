package com.xiaoyiyiyo.server.repository;

/**
 * Created by xiaoyiyiyo on 2018/4/27.
 */
public interface IRedisClient {

    String get(String key);
    String hget(String hkey, String key);

    String set(String key, String value);
    long hset(String hkey, String key, String value);

    long del(String key);
    long hdel(String hkey, String key);

    long expire(String key, int second);

}
