/**
 * <p>
 * 文件名称:    ExecutionJob
 * </p>
 */
package com.zhou.yadmin.system.scheduling.job;

import java.util.concurrent.Future;

import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.utils.ExceptionUtils;
import com.zhou.yadmin.common.utils.SpringUtils;
import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import com.zhou.yadmin.system.scheduling.domain.SchedulerLog;
import com.zhou.yadmin.system.scheduling.dto.SchedulerTask;
import com.zhou.yadmin.system.scheduling.repository.SchedulerLogRepository;
import com.zhou.yadmin.system.scheduling.service.SchedulerJobService;
import org.quartz.JobExecutionContext;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 21:49
 */
@Async
@Component
public class ExecutionJob extends QuartzJobBean
{
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Scheduler scheduler;
    @Autowired
    @Qualifier("customExecutor")
    private AsyncTaskExecutor executor;

    @Override
    protected void executeInternal(JobExecutionContext context)
    {
        SchedulerJob schedulerJob = (SchedulerJob) context.getMergedJobDataMap().get(CommonConstant.JOB_KEY);
        // 获取spring bean
        SchedulerLogRepository schedulerLogRepository = SpringUtils.getBean(SchedulerLogRepository.class);
        SchedulerJobService schedulerJobService = SpringUtils.getBean(SchedulerJobService.class);
        JobExecutor jobExecutor = SpringUtils.getBean(JobExecutor.class);

        SchedulerLog schedulerLog = new SchedulerLog();
        schedulerLog.setJobName(schedulerJob.getJobName());
        schedulerLog.setBeanName(schedulerJob.getBeanName());
        schedulerLog.setMethodName(schedulerJob.getMethodName());
        schedulerLog.setParams(schedulerJob.getParams());
        long startTime = System.currentTimeMillis();
        schedulerLog.setCronExpression(schedulerJob.getCronExpression());
        try
        {
            // 执行任务
            logger.info("任务准备执行，任务名称：{}", schedulerJob.getJobName());
            SchedulerTask task = new SchedulerTask(schedulerJob.getBeanName(), schedulerJob.getMethodName(), schedulerJob.getParams());
            Future<?> future = executor.submit(task);
            future.get();
            long times = System.currentTimeMillis() - startTime;
            schedulerLog.setTime(times);
            // 任务状态
            schedulerLog.setSuccess(true);
            logger.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", schedulerJob.getJobName(), times);
        }
        catch (Exception e)
        {
            logger.error("任务执行失败，任务名称：{}" + schedulerJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            schedulerLog.setTime(times);
            // 任务状态 0：成功 1：失败
            schedulerLog.setSuccess(false);
            schedulerLog.setExceptionDetail(ExceptionUtils.getStackTrace(e));
            //出错就暂停任务
            jobExecutor.pauseJob(schedulerJob);
            //更新状态
            schedulerJobService.updatePaused(schedulerJob);
        }
        finally
        {
            schedulerLogRepository.save(schedulerLog);
        }
    }
}
