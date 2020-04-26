/**
 * <p>
 * 文件名称:    JobRunner
 * </p>
 */
package com.zhou.yadmin.system.scheduling.config;

import java.util.List;

import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import com.zhou.yadmin.system.scheduling.job.JobExecutor;
import com.zhou.yadmin.system.scheduling.repository.SchedulerJobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 20:58
 */
@Component
public class JobRunner extends AbstractBaseComponent implements ApplicationRunner
{
    @Autowired
    private SchedulerJobRepository schedulerJobRepository;

    @Autowired
    private JobExecutor jobExecutor;

    /**
     * 项目启动时重新激活启用的定时任务
     *
     * @param applicationArguments
     *
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments applicationArguments)
    {
        logger.info("--------------------注入定时任务---------------------");
        List<SchedulerJob> jobList = schedulerJobRepository.findByPausedIsFalse();
        jobList.forEach(job -> jobExecutor.addJob(job));
        logger.info("--------------------定时任务注入完成---------------------");
    }
}
