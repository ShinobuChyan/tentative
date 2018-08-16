package com.tentative.api.service.user;

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

}
