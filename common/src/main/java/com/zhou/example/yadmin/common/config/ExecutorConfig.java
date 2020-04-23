/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.config.ThreadPoolConfig
 * </p>
 */
package com.zhou.example.yadmin.common.config;

import java.util.concurrent.ThreadPoolExecutor;

import com.zhou.example.yadmin.common.plugin.AbstractBaseComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 19:50
 */
@Configuration
public class ExecutorConfig extends AbstractBaseComponent
{
    // @Bean
    // public ExecutorService getThreadPool()
    // {
        // ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();
        // int size = 2;
        // ExecutorService executorService = new ThreadPoolExecutor(size,size,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),namedThreadFactory);
        // return executorService;
    //     return null;
    // }

    // @Bean
    // public Executor asyncServiceExecutor()
    // {
    //     // 返回可用处理器的虚拟机的最大数量不小于1
    //     int cpu = Runtime.getRuntime().availableProcessors() * 2;
    //     logger.info("start asyncServiceExecutor cpu : {}", cpu);
    //     int poolSize = cpu * 2;
    //     ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    //     //配置核心线程数
    //     executor.setCorePoolSize(poolSize);
    //     //配置最大线程数
    //     executor.setMaxPoolSize(poolSize);
    //     //配置队列大小
    //     executor.setQueueCapacity(50);
    //     //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
    //     executor.setWaitForTasksToCompleteOnShutdown(true);
    //     //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
    //     executor.setAwaitTerminationSeconds(60);
    //     //配置线程池中的线程的名称前缀
    //     executor.setThreadNamePrefix("async-service-");
    //     // rejection-policy：当pool已经达到max size的时候，如何处理新任务
    //     // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
    //     // 使用预定义的异常处理类
    //     executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    //     //执行初始化
    //     executor.initialize();
    //     return executor;
    // }

    @Bean("customExecutor")
    public ThreadPoolTaskExecutor customServiceExecutor()
    {
        // 返回可用处理器的虚拟机的最大数量不小于1
        int cpu = Runtime.getRuntime().availableProcessors();
        logger.info("start customServiceExecutor cpu : {}", cpu);
        int poolSize = cpu * 2;
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //线程核心数目
        executor.setCorePoolSize(poolSize);
        executor.setAllowCoreThreadTimeOut(true);
        //最大线程数
        executor.setMaxPoolSize(poolSize);
        //配置队列大小
        executor.setQueueCapacity(50);
        //用来设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        //配置线程池前缀
        executor.setThreadNamePrefix("custom-service-");
        //配置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //数据初始化
        executor.initialize();
        return executor;
    }
}
