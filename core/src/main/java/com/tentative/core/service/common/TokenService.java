package com.tentative.core.service.common;

import io.jsonwebtoken.Claims;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * JWT相关
 *
 * @author Shinobu
 * @since 2018/2/26
 */
public interface TokenService {

    /**
     * 生成用户身份token
     *
     * @param userId 用户ID
     * @param imei   设备号
     * @return token
     */
    @NotNull String genUserToken(@NotNull String userId, @NotNull String imei);

    /**
     * 解析token
     *
     * @param token token
     * @return 携带参数
     * @throws Exception 解析失败错误
     */
    @NotNull Claims decodeToken(@NotNull String token) throws Exception;

    /**
     * 验证token是否合法
     *
     * @param token token
     * @return isAvailable
     */
    boolean isNotAvailable(String token);

    /**
     * 检查是否用户正在生效中的token
     *
     * @param userId 用户ID
     * @param token  token
     * @return isActive
     */
    boolean isActive(String userId, String token);

    /**
     * 使token失效
     * @param token token
     * @return 是否成功
     */
    boolean invalidToken(String token);

    /**
     * 续签token
     *
     * @param oldToken 旧的token
     * @param exp      过期时间
     * @return newToken
     * @throws Exception 旧的token解析失败错误
     */
    @NotNull String renewToken(@NotNull String oldToken, @NotNull Date exp) throws Exception;

}
