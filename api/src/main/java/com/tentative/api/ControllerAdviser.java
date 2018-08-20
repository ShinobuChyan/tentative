package com.tentative.api;

import com.tentative.common.constant.CommonResultCode;
import com.tentative.common.exception.AssertFailedException;
import com.tentative.common.exception.RequestRefusedException;
import com.tentative.common.exception.RestRuntimeException;
import com.tentative.common.model.CommonResult;
import com.tentative.common.util.RequestThreadLocal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shinobu
 * @since 2018/8/14
 */
@ControllerAdvice
public class ControllerAdviser {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAdviser.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {

        LOGGER.error("[ExceptionHandler] caught: ", e);

        // 访问拒绝
        if (e instanceof RequestRefusedException) {
            LOGGER.warn("[访问拒绝] info: " + RequestThreadLocal.detail());
            LOGGER.warn("[访问拒绝] msg : " + e.getMessage());
            return CommonResult.newFailedResult(e.getMessage(), null, CommonResultCode.FORBIDDEN);
        }
        // 断言失败
        if (e instanceof AssertFailedException) {
            LOGGER.warn("[断言失败] msg: " + e.getMessage());
            return CommonResult.newFailedResult(e.getMessage(), null, CommonResultCode.BAD_REQUEST);
        }
        // api入参校验失败
        if (e instanceof MethodArgumentNotValidException) {
            List<String> errorMsg = ((MethodArgumentNotValidException) e).getBindingResult().getFieldErrors().stream()
                    .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                    .collect(Collectors.toList());
            LOGGER.warn("[api入参校验失败] msg: " + errorMsg);
            return CommonResult.newFailedResult("参数校验失败", errorMsg, CommonResultCode.BAD_REQUEST);
        }
        // 业务控制抛错
        if (e instanceof RestRuntimeException) {
            return CommonResult.newFailedResult(e.getMessage(), ((RestRuntimeException) e).getData(), null);
        }
        // Redis连接超时
        if (e instanceof RedisConnectionFailureException) {
            LOGGER.warn("[Redis连接超时]: " + e.getMessage());
            return CommonResult.newFailedResult("网络延迟，请稍后重试", null, CommonResultCode.TIME_OUT);
        }

        return CommonResult.newFailedResult("系统错误，请联系相关人员", null, CommonResultCode.INTERNAL_SERVER_ERROR);
    }

}
