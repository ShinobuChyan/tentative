package com.tentative.common.model;

import com.tentative.common.constant.CommonResultCode;

import javax.validation.constraints.NotNull;

/**
 * @author Shinobu
 * @since 2017/10/26
 */
public class CommonResult {

    public enum Status {
        /**
         * 业务结果状态
         */
        SUCCESS, FAILED
    }

    private Status status;

    private String code;

    private String message;

    private Object data;

    private CommonResult(Status status, String code, String message, Object data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static CommonResult newSuccessResult(@NotNull String message, Object data, String code) {
        return new CommonResult(Status.SUCCESS, code == null ? CommonResultCode.COMMON_SUCCESS : code, message, data);
    }

    public static CommonResult newFailedResult(@NotNull String message, Object data, String code) {
        return new CommonResult(Status.FAILED, code == null ? CommonResultCode.COMMON_FAILED : code, message, data);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
