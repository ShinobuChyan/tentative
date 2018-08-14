package com.tentative.common.exception;

/**
 * 业务控制抛出的异常
 *
 * @author Shinobu
 * @since 2018/2/27
 */
public class RestRuntimeException extends RuntimeException {

    private final static long serialVersionUID = 1233968128635674081L;

    private Object data;

    public RestRuntimeException(String message) {
        super(message);
    }

    public RestRuntimeException(String message, Object data) {
        super(message);
        this.data = data;
    }

    public RestRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestRuntimeException(String message, Throwable cause, Object data) {
        super(message, cause);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
