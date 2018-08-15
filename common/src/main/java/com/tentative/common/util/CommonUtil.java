package com.tentative.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 通用未分类方法
 *
 * @author Shinobu
 * @since 2018/3/6
 */
public class CommonUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 实体类同名属性互传
     *
     * @param source 源实体
     * @param target 目标实体
     */
    public static void attrTransfer(Object source, Object target) {

        Class sClass = source.getClass();
        Class tClass = target.getClass();

        Field[] sFields = sClass.getDeclaredFields();

        for (Field sField : sFields) {

            if ("serialVersionUID".equals(sField.getName())) {
                continue;
            }

            try {
                Field tField = tClass.getDeclaredField(sField.getName());
                sField.setAccessible(true);
                tField.setAccessible(true);
                tField.set(target, sField.get(source));
            } catch (Exception ignore) {
            }
        }
    }

    /**
     * 获取当前网络ip
     *
     * 网上抄来的
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if ("127.0.0.1".equals(ipAddress) || "0:0:0:0:0:0:0:1".equals(ipAddress)) {
                //根据网卡取本机配置的IP
                InetAddress iNet = null;
                try {
                    iNet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                if (iNet != null) {
                    ipAddress = iNet.getHostAddress();
                }
            }
        }
        //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        //"***.***.***.***".length() = 15
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    /**
     * 手机号脱敏
     */
    public static String desensitizeNumber(String number) {
        int phoneFixedLength = 11;
        int certFixedLength = 18;
        int length = number == null ? 0 : number.length();
        if (length != phoneFixedLength && length != certFixedLength) {
            LOGGER.warn("[手机号/身份证号脱敏] -> 非正常号码: " + number);
            return number;
        }
        return number.substring(0, 3) + " **** " + number.substring(number.length() - 4, number.length());
    }

}
