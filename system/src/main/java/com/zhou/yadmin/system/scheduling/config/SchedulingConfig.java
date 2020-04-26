/**
 * <p>
 * 文件名称:    SchedulingConfig
 * </p>
 */
package com.zhou.yadmin.system.scheduling.config;

import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 定时任务配置
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 21:01
 */
@Configuration
public class SchedulingConfig
{
    /**
     * 解决Job中注入Spring Bean为null的问题
     */
    @Component("schedulerJobFactory")
    public static class SchedulerJobFactory extends AdaptableJobFactory
    {
        @Autowired
        private AutowireCapableBeanFactory capableBeanFactory;

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception
        {
            //调用父类的方法
            Object jobInstance = super.createJobInstance(bundle);
            capableBeanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

    /**
     * 注入scheduler到spring
     *
     * @param schedulerJobFactory
     *
     * @return
     *
     * @throws Exception
     */
    @Bean(name = "scheduler")
    public Scheduler scheduler(SchedulerJobFactory schedulerJobFactory) throws Exception
    {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        factoryBean.setJobFactory(schedulerJobFactory);
        factoryBean.afterPropertiesSet();
        Scheduler scheduler = factoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }
}
