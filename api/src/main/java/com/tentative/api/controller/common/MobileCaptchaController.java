package com.tentative.api.controller.common;

import com.tentative.common.model.CommonResult;
import com.tentative.common.util.Assert;
import com.tentative.core.model.common.MobileCaptchaEntity;
import com.tentative.core.service.common.MobileCaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Shinobu
 * @since 2018/8/15
 */
@RestController
@RequestMapping("/captcha/mobile")
public class MobileCaptchaController {

    private final MobileCaptchaService mobileCaptchaService;

    @Autowired
    public MobileCaptchaController(MobileCaptchaService mobileCaptchaService) {
        this.mobileCaptchaService = mobileCaptchaService;
    }

    /**
     * 通用验证码 - 发送短信验证码（六位随机数字，有效期十分钟）
     *
     * @param phoneNumber 手机号
     */
    @GetMapping("/common/send")
    public CommonResult sendCommonCaptcha(String phoneNumber) {
        Assert.notNull(phoneNumber);
        mobileCaptchaService.sendNewCommonCaptcha(phoneNumber);
        return CommonResult.newSuccessResult("验证码发送成功，请注意查收", null, null);
    }

}
