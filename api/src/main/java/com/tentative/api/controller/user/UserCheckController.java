package com.tentative.api.controller.user;

import com.tentative.common.model.CommonResult;
import com.tentative.core.service.user.UserPeripheralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关 - 外围
 *
 * @author Shinobu
 * @since 2018/8/15
 */
@RestController
@RequestMapping("/user/check")
public class UserCheckController {

    private final UserPeripheralService userPeripheralService;

    @Autowired
    public UserCheckController(UserPeripheralService userPeripheralService) {
        this.userPeripheralService = userPeripheralService;
    }

    /**
     * 检查手机号是否可用
     *
     * @param phoneNumber 手机号
     */
    @GetMapping("/phoneNumber")
    public CommonResult isPhoneNumberAvailable(String phoneNumber) {
        return CommonResult.newSuccessResult("查询成功", userPeripheralService.isPhoneNumberAvailable(phoneNumber), null);
    }

    /**
     * 检查用户昵称是否可用
     *
     * @param nickname 用户昵称
     */
    @GetMapping("/nickname")
    public CommonResult isNicknameAvailable(String nickname) {
        return CommonResult.newSuccessResult("查询成功", userPeripheralService.isNicknameAvailable(nickname), null);
    }

}
