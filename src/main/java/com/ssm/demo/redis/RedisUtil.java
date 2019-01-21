package com.ssm.demo.redis;

import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by 13 on 2018/9/7.
 */
@Component
public class RedisUtil {

    private static final String CACHE_NAME = "ssm-demo-cache:";
    // 过期时间
    private static final int EXPIRE_TIME = 3000;

    private RedisTemplate template;

    private RedisCache cache;

    public RedisUtil() {
        init();
    }

    public void init() {
        template = SpringUtil.getBean("redisTemplate");//RedisCacheConfig中定义了
        cache = new RedisCache(CACHE_NAME, CACHE_NAME.getBytes(), template, EXPIRE_TIME);
    }

    /**
     * 添加
     * @param key
     * @param obj
     */
    public void put(String key, Object obj) {
        cache.put(key, obj);
    }

    /**
     * 获取
     * @param key
     * @param clazz
     * @return
     */
    public Object get(String key, Class clazz) {
        return cache.get(key) == null ? null : cache.get(key, clazz);
    }

    /**
     * 删除
     * @param key
     */
    public void del(String key) {
        cache.evict(key);
    }
}