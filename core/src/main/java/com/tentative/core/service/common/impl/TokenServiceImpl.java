package com.tentative.core.service.common.impl;

import com.tentative.core.bean.Tools;
import com.tentative.core.bean.Values;
import com.tentative.core.service.common.TokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@Service
public class TokenServiceImpl implements TokenService {

    private final static String SIGN_KEY_DEFAULT = "Shinobu NO.1";

    @Resource
    private Tools tools;

    @Resource
    private Values values;

    /**
     * 生成用户身份token
     *
     * @param userId 用户ID
     * @param imei   设备号
     * @return token
     */
    @Override
    public @NotNull String genUserToken(@NotNull String userId, @NotNull String imei) {
        // 签发时间
        Date iat = new Date();
        // 过期时间，15天
        Date exp = new Date(iat.getTime() + (1000 * 60 * 60 * 24 * 15));
        Map<String, Object> claims = new HashMap<>(2, 1);
        claims.put("userId", userId);
        claims.put("imei", imei);

        String token = Jwts.builder()
                .setClaims(claims)
                // 签发者
                .setIssuer("Tentative Main Server")
                .setIssuedAt(iat)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY_DEFAULT)
                .compact();
        long timeout = exp.getTime() - System.currentTimeMillis();
        tools.stringRedisTemplate.opsForValue().set(values.redisKeys.activeUserTokenKey + userId, token, timeout, TimeUnit.MILLISECONDS);
        return token;
    }

    /**
     * 解析token
     *
     * @param token token
     * @return 携带参数
     */
    @Override
    public @NotNull Claims decodeToken(@NotNull String token) {
        return Jwts.parser().setSigningKey(SIGN_KEY_DEFAULT).parseClaimsJws(token).getBody();
    }

    /**
     * 验证token是否合法
     *
     * @param token token
     * @return isAvailable
     */
    @Override
    public boolean isNotAvailable(@NotNull String token) {

        if (tools.stringRedisTemplate.opsForValue().get(values.redisKeys.inactiveUserTokenKey + token) != null) {
            return true;
        }

        try {
            decodeToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    /**
     * 检查是否用户正在生效中的token
     *
     * @param userId 用户ID
     * @param token  token
     * @return isActive
     */
    @Override
    public boolean isActive(@NotNull String userId, @NotNull String token) {
        String activeToken = tools.stringRedisTemplate.opsForValue().get(values.redisKeys.activeUserTokenKey + userId);
        return token.equals(activeToken);
    }

    /**
     * 使token失效
     *
     * @param token token
     * @return 是否成功
     */
    @Override
    public boolean invalidToken(String token) {

        if (isNotAvailable(token)) {
            return true;
        }

        Claims tokenClaims = decodeToken(token);
        long timeout = tokenClaims.getExpiration().getTime() - System.currentTimeMillis();
        if (timeout > 0) {
            tools.stringRedisTemplate.opsForValue().set(values.redisKeys.inactiveUserTokenKey + token, token, timeout, TimeUnit.MILLISECONDS);
        }

        return true;
    }

    /**
     * 续签token
     *
     * @param oldToken 旧的token
     * @param exp      过期时间
     * @return newToken
     */
    @Override
    public @NotNull String renewToken(@NotNull String oldToken, @NotNull Date exp) {

        Claims oldClaim = decodeToken(oldToken);
        long timeout = oldClaim.getExpiration().getTime() - System.currentTimeMillis();
        if (timeout > 0) {
            tools.stringRedisTemplate.opsForValue().set(values.redisKeys.inactiveUserTokenKey + oldToken, oldToken, timeout, TimeUnit.MILLISECONDS);
        }

        String userId = (String) oldClaim.get("userId");
        oldClaim.setIssuedAt(new Date());
        oldClaim.setExpiration(exp);

        String newToken = customToken(oldClaim);
        tools.stringRedisTemplate.opsForValue().set(values.redisKeys.activeUserTokenKey + userId, newToken, exp.getTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        return newToken;
    }

    /**
     * 自定义token
     *
     * @param claims 参数
     * @return token
     */
    private String customToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SIGN_KEY_DEFAULT)
                .compact();
    }
}
