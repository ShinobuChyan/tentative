package com.tentative.security;

import com.tentative.common.constant.ValueConstant;
import com.tentative.common.exception.RequestRefusedException;
import com.tentative.core.entity.User;
import com.tentative.core.service.common.TokenService;
import com.tentative.core.service.user.UserPeripheryService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 身份令牌拦截器
 *
 * - preHandle:  null及黑名单验证、有效性验证、单点登录验证
 * - postHandle: token续签
 * - after:      thread log
 *
 * @author Shinobu
 * @since 2018/8/20
 */
@Component
@Order(0)
public class Order0Interceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Autowired
    public Order0Interceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        String token = request.getHeader(ValueConstant.REQUEST_HEADER_NAME_USER_TOKEN);
        if (token == null || tokenService.isNotAvailable(token)) {
            throw new RequestRefusedException("身份无效或已过期，请重新登陆");
        }
        Claims claims;
        try {
            claims = tokenService.decodeToken(token);
        } catch (Exception e) {
            throw new RequestRefusedException("身份无效或已过期，请重新登陆");
        }
        String userId = (String) claims.get("userId");
        if (!tokenService.isActive(userId, token)) {
            throw new RequestRefusedException("账户已在其他地方登陆");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }
}
