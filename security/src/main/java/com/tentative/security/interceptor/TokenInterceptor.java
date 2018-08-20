package com.tentative.security.interceptor;

import com.tentative.common.constant.ValueConstant;
import com.tentative.common.exception.RequestRefusedException;
import com.tentative.common.util.CommonUtil;
import com.tentative.common.util.RequestThreadLocal;
import com.tentative.core.service.common.TokenService;
import com.tentative.core.service.common.impl.TokenServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 身份令牌拦截器
 *
 * - preHandle:  null及黑名单验证、有效性验证、单点登录验证
 * - postHandle: token续签、request log
 * - after:      clear threadLocal
 *
 * @author Shinobu
 * @since 2018/8/20
 */
@Component
@Order(0)
public class TokenInterceptor implements HandlerInterceptor {

    /**
     * token续签：剩余小于三天时续签
     */
    private final static int RENEW_LEFT_TIME = 1000 * 60 * 60 * 24 * 3;

    private final TokenService tokenService;

    @Value("${server.servlet.context-path}")
    private String serverContextPath;

    @Autowired
    public TokenInterceptor(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 404
        if (handler instanceof ResourceHttpRequestHandler) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return false;
        }

        String url = removeContextPath(request.getRequestURI());
        String ip = CommonUtil.getIpAddr(request);
        RequestThreadLocal.init(url, ip);
        String token = request.getHeader(ValueConstant.REQUEST_HEADER_NAME_USER_TOKEN);
        RequestThreadLocal.setToken(token);
        // 有效性验证
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
        String imei = (String) claims.get("imei");
        RequestThreadLocal.setUserId(userId);
        RequestThreadLocal.setImei(imei);

        // 单点登录验证
        if (!tokenService.isActive(userId, token)) {
            throw new RequestRefusedException("账户已在其他地方登陆");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {

        String token = RequestThreadLocal.getToken();

        // 续签
        long now = System.currentTimeMillis();
        try {
            Claims claims = tokenService.decodeToken(token);
            if (claims.getExpiration().getTime() - now <= RENEW_LEFT_TIME) {
                String newToken = tokenService.renewToken(token, new Date(now + TokenServiceImpl.DEFAULT_TOKEN_EXP));
                response.setHeader(ValueConstant.REQUEST_HEADER_NAME_USER_TOKEN, newToken);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestThreadLocal.clear();
    }

    private String removeContextPath(String url) {
        if (serverContextPath == null || serverContextPath.isEmpty()) {
            throw new RuntimeException("server context-path is absent");
        }
        return url.replace(serverContextPath, "");
    }
}
