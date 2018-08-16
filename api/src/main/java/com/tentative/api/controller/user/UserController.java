package com.tentative.api.controller.user;

import com.tentative.common.model.CommonResult;
import com.tentative.core.entity.User;
import com.tentative.core.model.user.UserCommonDTO;
import com.tentative.common.util.Assert;
import com.tentative.core.service.common.MobileCaptchaService;
import com.tentative.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 用户相关 - 核心
 *
 * @author Shinobu
 * @since 2018/8/13
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final MobileCaptchaService mobileCaptchaService;

    @Autowired
    public UserController(UserService userService, MobileCaptchaService mobileCaptchaService) {
        this.userService = userService;
        this.mobileCaptchaService = mobileCaptchaService;
    }

    /**
     * 用户注册
     *
     * @param dto dto
     * @return userInfo
     */
    @PostMapping("/reg/captcha")
    public CommonResult register(@RequestBody @Validated UserCommonDTO dto) {
        Assert.notNull(dto.getPhoneNumber(), dto.getCaptcha(), dto.getNickname());
        mobileCaptchaService.consumeCommonCaptcha(dto.getPhoneNumber(), dto.getCaptcha());
        User user = userService.regByCaptcha(dto.getPhoneNumber(), dto.getNickname());
        return CommonResult.newSuccessResult("注册成功", user.desensitization(), null);
    }

}
