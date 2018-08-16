package com.tentative.api.service.user.impl;

import com.tentative.api.service.user.UserPeripheryService;
import com.tentative.api.service.user.UserService;
import com.tentative.common.constant.StatusConstant;
import com.tentative.common.constant.TypeConstant;
import com.tentative.common.constant.ValueConstant;
import com.tentative.common.exception.RestRuntimeException;
import com.tentative.core.bean.Mappers;
import com.tentative.core.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * 用户相关 - 核心服务
 *
 * @author Shinobu
 * @since 2018/8/15
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private Mappers mappers;

    @Resource
    private UserPeripheryService userPeripheryService;

    /**
     * 用户注册 -> 通过手机验证码
     *
     * @param phoneNumber 手机号
     * @param nickname    昵称
     * @return user
     */
    @Override
    public User regByCaptcha(@NotNull String phoneNumber, @NotNull String nickname) {

        if (!userPeripheryService.isPhoneNumberAvailable(phoneNumber) || !userPeripheryService.isNicknameAvailable(nickname)) {
            throw new RestRuntimeException("手机号或昵称不可用");
        }

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
        newUser.setProfileId(UUID.randomUUID().toString());
        newUser.setPhoneNumber(phoneNumber);
        newUser.setType(TypeConstant.USER_TYPE_NORMAL);
        newUser.setStatus(StatusConstant.USER_STATUS_NORMAL);
        newUser.setNickname(nickname);
        newUser.setCreatorId(ValueConstant.SYSTEM_CREATOR_INFO);
        newUser.setCreatorName(ValueConstant.SYSTEM_CREATOR_INFO);

        int i = mappers.userMapper.insertCorrectly(newUser);
        if (i != 1) {
            throw new RestRuntimeException("注册失败");
        }
        // TODO insert profile info
        return newUser;
    }
}
