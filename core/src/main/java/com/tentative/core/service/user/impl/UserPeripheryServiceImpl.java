package com.tentative.core.service.user.impl;

import com.tentative.core.entity.User;
import com.tentative.core.service.user.UserPeripheryService;
import com.tentative.core.bean.Mappers;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * 用户相关 - 外围服务
 *
 * @author Shinobu
 * @since 2018/8/15
 */
@Service
public class UserPeripheryServiceImpl implements UserPeripheryService {

    @Resource
    private Mappers mappers;

    /**
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return user
     */
    @Override
    public @Nullable
    User getAvailableByUserId(@NotNull String userId) {
        return mappers.userMapper.selectAvailableById(userId);
    }

    /**
     * 检查手机号是否可用
     *
     * @param phoneNumber 手机号
     * @return 是否可用
     */
    @Override
    public boolean isPhoneNumberAvailable(@NotNull String phoneNumber) {
        return mappers.userMapper.countPhoneNumberInAvailable(phoneNumber) < 1;
    }

    /**
     * 检查用户昵称是否可用
     *
     * @param nickname 用户昵称
     * @return 是否可用
     */
    @Override
    public boolean isNicknameAvailable(@NotNull String nickname) {
        return mappers.userMapper.countNicknameInAvailable(nickname) < 1;
    }

}
