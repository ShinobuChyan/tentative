package com.tentative.core.service.user.impl;

import com.tentative.core.entity.User;
import com.tentative.core.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

/**
 * 用户相关 - 核心服务
 *
 * @author Shinobu
 * @since 2018/8/15
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * 用户注册 -> 通过手机验证码
     *
     * @param phoneNumber 手机号
     * @param nickname    昵称
     * @return user
     */
    @Override
    public User regByCaptcha(@NotNull String phoneNumber, @NotNull String nickname) {
        // TODO
        return null;
    }
}
