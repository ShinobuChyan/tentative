package com.tentative.core.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tentative.common.enums.DeviceTypeEnum;
import com.tentative.common.util.RequestThreadLocal;
import com.tentative.core.bean.Values;
import com.tentative.core.service.common.TokenService;
import com.tentative.core.service.user.UserPeripheralService;
import com.tentative.core.service.user.UserService;
import com.tentative.common.constant.StatusConstant;
import com.tentative.common.constant.TypeConstant;
import com.tentative.common.constant.ValueConstant;
import com.tentative.common.exception.RestRuntimeException;
import com.tentative.core.bean.Mappers;
import com.tentative.core.entity.User;
import org.springframework.data.redis.core.StringRedisTemplate;
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
    private Values values;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserPeripheralService userPeripheralService;

    @Resource
    private TokenService tokenService;

    /**
     * 用户注册 -> 通过手机验证码
     *
     * @param phoneNumber 手机号
     * @param nickname    昵称
     * @return user
     */
    @Override
    public User regByCaptcha(@NotNull String phoneNumber, @NotNull String nickname) {

        if (!userPeripheralService.isPhoneNumberAvailable(phoneNumber) || !userPeripheralService.isNicknameAvailable(nickname)) {
            throw new RestRuntimeException("手机号或昵称不可用");
        }

        User newUser = new User();
        newUser.setId(UUID.randomUUID().toString());
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
        return newUser;
    }

    /**
     * 登录
     *
     * @param phoneNumber 手机号
     * @param deviceType  设备类型
     * @param pushId      设备消息推送ID
     * @return user
     */
    @Override
    public User login(@NotNull String phoneNumber, @NotNull DeviceTypeEnum deviceType, @NotNull String pushId) {
        User user = mappers.userMapper.selectAvailableByPhoneNumber(phoneNumber);
        if (user == null) {
            throw new RestRuntimeException("用户不存在");
        }
        if (StatusConstant.USER_STATUS_BLOCK.equals(user.getStatus())) {
            throw new RestRuntimeException("用户已被冻结");
        }

        // 设备推送ID注册
        JSONObject pushInfo = new JSONObject();
        pushInfo.put("type", deviceType.name());
        pushInfo.put("pushId", pushId);
        stringRedisTemplate.opsForHash().put(values.redisKeys.devicePushInfoKey, user.getId(), JSON.toJSONString(pushInfo));
        // 生成身份令牌
        String userToken = tokenService.genUserToken(user.getId(), RequestThreadLocal.getImei());
        RequestThreadLocal.setToken(userToken);
        return user;
    }

    /**
     * 登出
     *
     * @param token 用户身份令牌
     */
    @Override
    public void logout(@NotNull String token) {
        tokenService.invalidToken(token);
    }

}
