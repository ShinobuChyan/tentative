package com.tentative.core.service.user;

import com.tentative.common.enums.DeviceTypeEnum;
import com.tentative.core.entity.User;

import javax.validation.constraints.NotNull;

/**
 * 用户相关 - 核心服务
 *
 * @author Shinobu
 * @since 2018/8/15
 */
public interface UserService {

    /**
     * 用户注册 -> 通过手机验证码
     *
     * @param phoneNumber 手机号
     * @param nickname    昵称
     * @return user
     */
    User regByCaptcha(@NotNull String phoneNumber, @NotNull String nickname);

    /**
     * 登录
     *
     * @param phoneNumber 手机号
     * @param deviceType  设备类型
     * @param pushId      设备消息推送ID
     * @return user
     */
    User login(@NotNull String phoneNumber, @NotNull DeviceTypeEnum deviceType, @NotNull String pushId);

    /**
     * 登出
     *
     * @param token 用户身份令牌
     */
    void logout(@NotNull String token);

}
