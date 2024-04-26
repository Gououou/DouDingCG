package com.douding.doudingcg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Guo
 * @create 2024-04-26 11:37
 */
@Slf4j
@Configuration
public class AsyncTaskPoolConfig {
    /**
     * 核心线程数
     * 默认的核心线程数为1
     *
     */
    private static final int CORE_POOL_SIZE = 10;
    /**
     * 最大线程数
     * 默认的最大线程数是Integer.MAX_VALUE 即2<sup>31</sup>-1
     */
    private static final int MAX_POOL_SIZE = 70;
    /**
     * 缓冲队列数
     * 默认的缓冲队列数是Integer.MAX_VALUE 即2<sup>31</sup>-1
     */
    private static final int QUEUE_CAPACITY = 500;

    /**
     * 允许线程空闲时间
     * 默认的线程空闲时间为60秒
     */
    private static final int KEEP_ALIVE_SECONDS = 300;

    /**
     * 线程池前缀名
     */
    private static final String THREAD_NAME_PREFIX = "DouDing_Async_Name_";

    /**
     * allowCoreThreadTimeOut为true则线程池数量最后销毁到0个
     * allowCoreThreadTimeOut为false
     * 销毁机制：超过核心线程数时，而且（超过最大值或者timeout过），就会销毁。
     * 默认是false
     */
    private static final boolean ALLOW_CORE_THREAD_TIME_OUT = false;

    @Bean("DouDingExecutor")
    public ThreadPoolTaskExecutor pmmExecutor(){
        ThreadPoolTaskExecutor taskExecutorExport = new ThreadPoolTaskExecutor();
        taskExecutorExport.setCorePoolSize(CORE_POOL_SIZE);
        taskExecutorExport.setMaxPoolSize(MAX_POOL_SIZE);
        taskExecutorExport.setQueueCapacity(QUEUE_CAPACITY);
        taskExecutorExport.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        taskExecutorExport.setThreadNamePrefix(THREAD_NAME_PREFIX);
        taskExecutorExport.setAllowCoreThreadTimeOut(ALLOW_CORE_THREAD_TIME_OUT);
        taskExecutorExport.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程池初始化
        taskExecutorExport.initialize();
        return taskExecutorExport;
    }
}
