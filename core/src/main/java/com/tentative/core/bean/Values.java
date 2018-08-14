package com.tentative.core.bean;

import com.tentative.core.bean.values.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@Component
public class Values {

    public final RedisKeys redisKeys;

    @Autowired
    public Values(RedisKeys redisKeys) {
        this.redisKeys = redisKeys;
    }
}