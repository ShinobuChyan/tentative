package com.tentative.common.util;

import com.alibaba.fastjson.JSON;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 请求公用变量
 *
 * @author Shinobu
 * @since 2018/8/20
 */
public class RequestThreadLocal {

    private final static ThreadLocal<HashMap<String, Object>> INFO = new ThreadLocal<>();

    /**
     * 初始化
     *
     * @param url 请求url
     * @param ip  请求ip
     */
    public static void init(@NotNull String url, @NotNull String ip) {
        if (INFO.get() != null) {
            throw new RuntimeException("[RequestThreadLocal] info has been initialized");
        }
        LinkedHashMap<String, Object> infoMap = new LinkedHashMap<>(32);
        infoMap.put("url", url);
        infoMap.put("ip", ip);
        INFO.set(infoMap);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> detail() {
        return (Map<String, Object>) INFO.get().clone();
    }

    /**
     * @return 内容详情
     */
    public static String stringDetail() {
        return JSON.toJSONString(INFO.get());
    }

    public static void setUrl(String url) {
        INFO.get().put("url", url);
    }

    public static String getUrl() {
        return (String) INFO.get().get("url");
    }

    public static void setIp(String ip) {
        INFO.get().put("ip", ip);
    }

    public static String getIp() {
        return (String) INFO.get().get("ip");
    }

    public static void setToken(String token) {
        INFO.get().put("token", token);
    }

    public static String getToken() {
        return (String) INFO.get().get("token");
    }

    public static void setUserId(String userId) {
        INFO.get().put("userId", userId);
    }

    public static String getUserId() {
        return (String) INFO.get().get("userId");
    }

    public static void setImei(String imei) {
        INFO.get().put("imei", imei);
    }

    public static String getImei() {
        return (String) INFO.get().get("imei");
    }

    public static void setNickname(String nickname) {
        INFO.get().put("nickname", nickname);
    }

    public static String getNickname() {
        return (String) INFO.get().get("nickname");
    }

    public static void clear() {
        INFO.remove();
    }

}
