package com.tentative.core.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@Component
public class Tools {

    public final StringRedisTemplate stringRedisTemplate;

    @Autowired
    public Tools(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
}
