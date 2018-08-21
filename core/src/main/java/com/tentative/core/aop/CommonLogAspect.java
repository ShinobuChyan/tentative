package com.tentative.core.aop;

import com.tentative.common.util.GrayLogUtil;
import com.tentative.common.util.RequestThreadLocal;
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
    public void methodPointcut() {}

    /**
     * 接口访问日志
     */
    @Before("methodPointcut()")
    public void requestLog() {
        GrayLogUtil.newAccessLog(RequestThreadLocal.detail());
    }

}
