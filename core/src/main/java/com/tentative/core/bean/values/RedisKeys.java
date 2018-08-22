package com.tentative.core.bean.values;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@Component
public class RedisKeys {

    public final String activeUserTokenKey;

    public final String inactiveUserTokenKey;

    public final String commonMobileCaptchaKey;

    public final String devicePushInfoKey;

    @Autowired
    RedisKeys(@Value("${customized.redis-key.user-token.active}") String activeUserTokenKey,
              @Value("${customized.redis-key.user-token.inactive}") String inactiveUserTokenKey,
              @Value("${customized.redis-key.captcha.mobile.common}") String commonMobileCaptchaKey,
              @Value("${customized.redis-key.mobile.push-info}") String devicePushInfoKey) {
        this.activeUserTokenKey = activeUserTokenKey;
        this.inactiveUserTokenKey = inactiveUserTokenKey;
        this.commonMobileCaptchaKey = commonMobileCaptchaKey;
        this.devicePushInfoKey = devicePushInfoKey;
    }
}

