package com.tentative.api.aop;

import com.tentative.common.util.GrayLogUtil;
import com.tentative.common.util.RequestThreadLocal;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志相关切面
 *
 * @author Shinobu
 * @since 2018/8/20
 */
@Component
@Aspect
@Order(0)
public class CommonLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonLogAspect.class);

    @Pointcut("execution(* com.tentative.*.controller.*.*.*(..))")
    public void controllerMethodsPointcut() {}

    @Pointcut("execution(* com.tentative.security.interceptor.*.preHandle(..))")
    public void interceptorPreHandlersPointcut() {}

    /**
     * 接口访问日志
     */
    @Before("controllerMethodsPointcut()")
    public void accessLog() {
        GrayLogUtil.newDefaultLog(GrayLogUtil.TOPIC_ACCESS_LOG, RequestThreadLocal.detail());
    }

    /**
     * 访问拒绝日志
     */
    @AfterThrowing("interceptorPreHandlersPointcut()")
    public void accessDeniedLog() {
        GrayLogUtil.newDefaultLog(GrayLogUtil.TOPIC_ACCESS_DENIED_LOG, RequestThreadLocal.detail());
    }

}
