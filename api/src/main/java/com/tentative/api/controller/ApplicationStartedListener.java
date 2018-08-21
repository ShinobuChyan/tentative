package com.tentative.api.controller;

import com.tentative.common.util.GrayLogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 应用初始化
 *
 * @author Shinobu
 * @since 2018/8/21
 */
@Component
public class ApplicationStartedListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${customized.gray-log.host}")
    private String grayLogHost;
    @Value("${customized.gray-log.port}")
    private String grayLogPort;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        initGrayLogUtil();
    }

    private void initGrayLogUtil() {
        GrayLogUtil.init(grayLogHost, Integer.valueOf(grayLogPort));
    }
}
