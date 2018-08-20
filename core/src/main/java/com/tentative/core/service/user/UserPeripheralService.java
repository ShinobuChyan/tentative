package com.tentative.core.service.user;

import com.tentative.core.entity.User;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

/**
 * 用户相关 - 外围服务
 *
 * @author Shinobu
 * @since 2018/8/15
 */
public interface UserPeripheralService {

    /**
     * 根据用户ID查询
     *
     * @param userId 用户ID
     * @return user
     */
    @Nullable User getAvailableByUserId(@NotNull String userId);

    /**
     * 检查手机号是否可用
     *
     * @param phoneNumber 手机号
     * @return 是否可用
     */
    boolean isPhoneNumberAvailable(@NotNull String phoneNumber);

    /**
     * 检查用户昵称是否可用
     *
     * @param nickname 用户昵称
     * @return 是否可用
     */
    boolean isNicknameAvailable(@NotNull String nickname);

}
