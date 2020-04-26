/**
 * <p>
 * 文件名称:    JobExecutor
 * </p>
 */
package com.zhou.yadmin.system.scheduling.job;

import javax.annotation.Resource;
import java.util.Date;

import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 22:23
 */
@Component
public class JobExecutor extends AbstractBaseComponent
{

    @Resource(name = "scheduler")
    private Scheduler scheduler;

    public void addJob(SchedulerJob schedulerJob)
    {
        try
        {
            // 构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ExecutionJob.class).withIdentity(CommonConstant.JOB_NAME + schedulerJob.getId()).build();

            //通过触发器名和cron 表达式创建 Trigger
            Trigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(CommonConstant.JOB_NAME + schedulerJob.getId()).startNow()
              .withSchedule(CronScheduleBuilder.cronSchedule(schedulerJob.getCronExpression())).build();
            cronTrigger.getJobDataMap().put(CommonConstant.JOB_KEY, schedulerJob);
            //重置启动时间
            ((CronTriggerImpl) cronTrigger).setStartTime(new Date());
            //执行定时任务
            scheduler.scheduleJob(jobDetail, cronTrigger);
            // 暂停任务
            if (Boolean.TRUE.equals(schedulerJob.getPaused()))
            {
                pauseJob(schedulerJob);
            }
        }
        catch (Exception e)
        {
            logger.error("创建定时任务失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }
    }

    /**
     * 更新job cron表达式
     *
     * @param schedulerJob
     *
     * @throws SchedulerException
     */
    public void updateJobCron(SchedulerJob schedulerJob)
    {
        try
        {
            TriggerKey triggerKey = TriggerKey.triggerKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedulerJob.getCronExpression());
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
            //重置启动时间
            ((CronTriggerImpl) trigger).setStartTime(new Date());
            scheduler.rescheduleJob(triggerKey, trigger);
            // 暂停任务
            if (Boolean.TRUE.equals(schedulerJob.getPaused()))
            {
                pauseJob(schedulerJob);
            }
        }
        catch (Exception e)
        {
            logger.error("更新定时任务失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }

    }

    /**
     * 删除一个job
     *
     * @param schedulerJob
     *
     * @throws SchedulerException
     */
    public void deleteJob(SchedulerJob schedulerJob)
    {
        try
        {
            JobKey jobKey = JobKey.jobKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            scheduler.deleteJob(jobKey);
        }
        catch (Exception e)
        {
            logger.error("删除定时任务失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }
    }

    /**
     * 恢复一个job
     *
     * @param schedulerJob
     *
     * @throws SchedulerException
     */
    public void resumeJob(SchedulerJob schedulerJob)
    {
        try
        {
            TriggerKey triggerKey = TriggerKey.triggerKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null)
            {
                addJob(schedulerJob);
            }
            JobKey jobKey = JobKey.jobKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            scheduler.resumeJob(jobKey);
        }
        catch (Exception e)
        {
            logger.error("恢复定时任务失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }
    }

    /**
     * 立即执行job
     *
     * @param schedulerJob
     *
     * @throws SchedulerException
     */
    public void runAJobNow(SchedulerJob schedulerJob)
    {
        try
        {
            TriggerKey triggerKey = TriggerKey.triggerKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            // 如果不存在则创建一个定时任务
            if (trigger == null)
            {
                addJob(schedulerJob);
            }
            JobDataMap dataMap = new JobDataMap();
            dataMap.put(CommonConstant.JOB_KEY, schedulerJob);
            JobKey jobKey = JobKey.jobKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            scheduler.triggerJob(jobKey, dataMap);
        }
        catch (Exception e)
        {
            logger.error("定时任务执行失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }
    }

    /**
     * 暂停一个job
     *
     * @param schedulerJob
     *
     * @throws SchedulerException
     */
    public void pauseJob(SchedulerJob schedulerJob)
    {
        try
        {
            JobKey jobKey = JobKey.jobKey(CommonConstant.JOB_NAME + schedulerJob.getId());
            scheduler.pauseJob(jobKey);
        }
        catch (Exception e)
        {
            logger.error("定时任务暂停失败", e);
            throw BadRequestException.newExceptionBySystem(e.getMessage(), e);
        }
    }
}
