package com.tentative.common.util;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求公用参数
 *
 * @author Shinobu
 * @since 2018/8/20
 */
public class RequestThreadLocal {

    private final static ThreadLocal<Map<String, Object>> INFO = new ThreadLocal<>();

    public static void init(@NotNull String token, @NotNull String url, @NotNull String userId, @NotNull String imei) {
        if (INFO.get() != null) {
            throw new RuntimeException("[RequestThreadLocal] info has been initialized");
        }
        Map<String, Object> infoMap = new HashMap<>(32);
        infoMap.put("token", token);
        infoMap.put("url", url);
        infoMap.put("userId", userId);
        infoMap.put("imei", imei);
        INFO.set(infoMap);
    }

    public static void clear() {
        INFO.remove();
    }

}
