package com.tentative.core.service.common;

import com.tentative.core.model.common.MobileCaptchaEntity;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * 手机验证码相关
 *
 * @author Shinobu
 * @since 2018/8/15
 */
public interface MobileCaptchaService {

    /**
     * 通用验证码 - 获取验证码实体
     *
     * @param phoneNumber 手机号
     * @return captchaEntity
     */
    @Nullable MobileCaptchaEntity getCommonCaptchaEntity(@NotNull String phoneNumber);

    /**
     * 通用验证码 - 发送短信验证码（六位随机数字，有效期十分钟）
     *
     * @param phoneNumber 手机号
     */
    void sendNewCommonCaptcha(@NotNull String phoneNumber);

    /**
     * 通用验证码 - 消费验证码
     *
     * @param phoneNumber 手机号
     * @param captcha     验证码
     */
    void consumeCommonCaptcha(@NotNull String phoneNumber, @NotNull String captcha);

}
