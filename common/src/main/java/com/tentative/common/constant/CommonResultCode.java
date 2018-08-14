package com.tentative.common.constant;

/**
 * @author Shinobu
 * @since 2018/8/13
 */
public class CommonResultCode {

    /**
     * 常规成功
     */
    public final static String COMMON_SUCCESS = "0000";

    /**
     * 常规失败
     */
    public final static String COMMON_FAILED = "1000";

    /**
     * 失败 - 参数校验、断言
     */
    public final static String BAD_REQUEST = "1400";

    /**
     * 失败 - redis连接超时
     */
    public final static String TIME_OUT = "1408";

    /**
     * 失败 - 缺省服务异常
     */
    public final static String INTERNAL_SERVER_ERROR = "1500";

}
