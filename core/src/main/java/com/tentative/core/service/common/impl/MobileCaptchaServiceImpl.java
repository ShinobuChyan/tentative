package com.tentative.core.service.common.impl;

import com.alibaba.fastjson.JSON;
import com.tentative.common.exception.RestRuntimeException;
import com.tentative.core.bean.Values;
import com.tentative.core.model.common.MobileCaptchaEntity;
import com.tentative.core.service.common.MobileCaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinobu
 * @since 2018/8/15
 */
@Service
public class MobileCaptchaServiceImpl implements MobileCaptchaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MobileCaptchaServiceImpl.class);

    /**
     * 默认验证码最小发送间隔，60秒
     */
    private final static int COMMON_MIN_SEND_INTERNAL = 60;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private Values values;

    @Resource
    private ExecutorService commonParallelTaskPool;

    /**
     * 通用验证码 - 获取验证码实体
     *
     * @param phoneNumber 手机号
     * @return captchaEntity
     */
    @Override
    public @Nullable MobileCaptchaEntity getCommonCaptchaEntity(@NotNull String phoneNumber) {
        String entityStr = stringRedisTemplate.opsForValue().get(values.redisKeys.commonMobileCaptchaKey + phoneNumber);
        return JSON.parseObject(entityStr, MobileCaptchaEntity.class);
    }

    /**
     * 通用验证码 - 发送短信验证码（六位随机数字，有效期十分钟）
     *
     * @param phoneNumber 手机号
     */
    @Override
    public void sendNewCommonCaptcha(@NotNull String phoneNumber) {

        MobileCaptchaEntity entity = getCommonCaptchaEntity(phoneNumber);
        if (entity != null && System.currentTimeMillis() - entity.getTime().getTime() < COMMON_MIN_SEND_INTERNAL) {
            throw new RestRuntimeException("请求过于频繁，请稍后再试");
        }

        String captcha = randomCaptcha6();
        commonParallelTaskPool.execute(() -> {
            // TODO send captcha
        });
        MobileCaptchaEntity newEntity = new MobileCaptchaEntity(captcha, new Date());
        stringRedisTemplate.opsForValue().set(values.redisKeys.commonMobileCaptchaKey + phoneNumber, newEntity.toString(), 10, TimeUnit.MINUTES);
        LOGGER.info("[通用短信验证码] phoneNumber: " + phoneNumber + "，captcha: " + captcha);
    }

    /**
     * 通用验证码 - 消费验证码
     *
     * @param phoneNumber 手机号
     * @param captcha     验证码
     */
    @Override
    public void consumeCommonCaptcha(@NotNull String phoneNumber, @NotNull String captcha) {
        String entityStr = stringRedisTemplate.opsForValue().get(values.redisKeys.commonMobileCaptchaKey + phoneNumber);
        MobileCaptchaEntity entity = JSON.parseObject(entityStr, MobileCaptchaEntity.class);
        if (entity != null && captcha.equals(entity.getCaptcha()) && deactivateCommonCaptcha(phoneNumber)) {
            return;
        }
        throw new RestRuntimeException("验证码无效或已过期");
    }

    /**
     * 清除验证码
     */
    private boolean deactivateCommonCaptcha(@NotNull String phoneNumber) {
        try {
            stringRedisTemplate.opsForValue().set(values.redisKeys.commonMobileCaptchaKey + phoneNumber, "", 1L, TimeUnit.MILLISECONDS);
            Thread.sleep(1);
            return true;
        } catch (Exception e) {
            throw new RestRuntimeException("[短信验证码] -> 默认验证码 -> 清除失败, phoneNumber: " + phoneNumber, e);
        }
    }

    /**
     * 随机六位短信验证码
     */
    private String randomCaptcha6() {
        Random random = new Random();
        return String.valueOf(random.nextInt(10)) +
                String.valueOf(random.nextInt(10)) +
                String.valueOf(random.nextInt(10)) +
                String.valueOf(random.nextInt(10)) +
                String.valueOf(random.nextInt(10)) +
                String.valueOf(random.nextInt(10));
    }
}
