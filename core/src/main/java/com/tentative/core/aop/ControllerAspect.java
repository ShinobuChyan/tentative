package com.tentative.core.aop;

import com.tentative.common.util.RequestThreadLocal;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * controller 相关切面
 *
 * @author Shinobu
 * @since 2018/8/20
 */
@Component
@Aspect
@Order(0)
public class ControllerAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAspect.class);

    /**
     * 所有controller方法
     */
    @Pointcut("execution(* com.tentative.*.controller.*.*.*(..))")
    public void methodPointcut() {}

    /**
     * 执行controller方法前打印、记录日志
     */
    @Before("methodPointcut()")
    public void requestLog() {
        LOGGER.debug("[请求入界] info: " + RequestThreadLocal.detail());
        // TODO 持久化
    }

}
