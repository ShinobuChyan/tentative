package com.tentative.common.util;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * GrayLog日志存储
 *
 * @author Shinobu
 * @since 2018/4/4
 */
public class GrayLogUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GrayLogUtil.class);

    /**
     * GELF日志主题 - 接口访问日志
     */
    public final static String TOPIC_ACCESS_LOG = "accessLog";

    /**
     * GELF日志主题 - 访问拒绝日志
     */
    public final static String TOPIC_ACCESS_DENIED_LOG = "accessDeniedLog";

    private static InetAddress address;

    private static Integer port;

    public static void init(String h, int p) {
        if (address != null || port != null) {
            throw new RuntimeException("GrayLogUtil has been initialized");
        }
        try {
            address = InetAddress.getByName(h);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e.getMessage());
        }
        port = p;
    }

    /**
     * 通用GELF日志
     *
     * @param topic       日志主题
     * @param extraParams 额外数据
     */
    public static void newDefaultLog(String topic, Map<String, Object> extraParams) {
        try {
            Map<String, Object> basicParams = basicParams(topic);
            appendParams(basicParams, extraParams);
            send(basicParams);
        } catch (Exception e) {
            LOGGER.error("[GrayLog] -> newInboundLog error.", e);
        }
    }

    private static Map<String, Object> basicParams(String topic) {
        Map<String, Object> params = new LinkedHashMap<>(32);
        params.put("version", "1.1");
        params.put("host", "MerchantPortal");
        params.put("short_message", topic);
        return params;
    }

    private static void appendParams(Map<String, Object> basicParams, Map<String, Object> extraParams) {
        for (Map.Entry<String, Object> entry : extraParams.entrySet()) {
            basicParams.put("_" + entry.getKey(), entry.getValue());
        }
    }


    private static void send(Map<String, Object> params) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        String content = JSON.toJSONString(params);
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, address, port);
        socket.send(packet);
        socket.close();
    }

}
