package com.tentative.security.interceptor;

import com.tentative.common.constant.StatusConstant;
import com.tentative.common.exception.RequestRefusedException;
import com.tentative.common.util.RequestThreadLocal;
import com.tentative.core.entity.User;
import com.tentative.core.service.user.UserPeripheralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户身份相关
 * - preHandle：用户有效性、用户状态
 *
 * @author Shinobu
 * @since 2018/8/20
 */
@Component
@Order(1)
public class UserInterceptor implements HandlerInterceptor {

    private final UserPeripheralService userPeripheralService;

    @Autowired
    public UserInterceptor(UserPeripheralService userPeripheralService) {
        this.userPeripheralService = userPeripheralService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        User user = userPeripheralService.getAvailableByUserId(RequestThreadLocal.getUserId());
        // 用户有效性校验
        if (user == null) {
            throw new RequestRefusedException("用户不存在");
        }
        RequestThreadLocal.setNickname(user.getNickname());
        // 用户状态校验
        if (StatusConstant.USER_STATUS_BLOCK.equals(user.getStatus())) {
            throw new RequestRefusedException("此用户已被冻结");
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
