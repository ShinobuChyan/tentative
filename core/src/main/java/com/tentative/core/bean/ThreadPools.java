package com.tentative.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Shinobu
 * @since 2018/8/15
 */
@Component
public class ThreadPools {

    /**
     * 默认最大阻塞队列长度
     */
    private final static int DEFAULT_TASK_QUEUE_MAX_LEN = 8192;

    /**
     * @return 通用并行任务线程池
     */
    @Bean
    public ExecutorService commonParallelTaskPool() {
        return new ThreadPoolExecutor(
                1,
                32,
                5L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(DEFAULT_TASK_QUEUE_MAX_LEN),
                new CustomizableThreadFactory("commonParallelTaskPool"));
    }

}
