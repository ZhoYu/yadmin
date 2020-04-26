/**
 * <p>
 * 文件名称:    SchedulerJobServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.scheduling.service.impl;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import com.zhou.yadmin.system.scheduling.job.JobExecutor;
import com.zhou.yadmin.system.scheduling.repository.SchedulerJobRepository;
import com.zhou.yadmin.system.scheduling.service.SchedulerJobService;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/30 19:17
 */
@Service
@CacheConfig(cacheNames = "schedulerJob")
public class SchedulerJobServiceImpl implements SchedulerJobService
{
    @Autowired
    private SchedulerJobRepository schedulerJobRepository;
    @Autowired
    private JobExecutor jobExecutor;

    /**
     * create
     *
     * @param resources
     *
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public SchedulerJob create(SchedulerJob resources)
    {
        if (!CronExpression.isValidExpression(resources.getCronExpression()))
        {
            throw BadRequestException.newExceptionBySystem("cron表达式格式错误");
        }
        resources = schedulerJobRepository.save(resources);
        jobExecutor.addJob(resources);
        return resources;
    }

    /**
     * update
     *
     * @param resources
     *
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void update(SchedulerJob resources)
    {
        if (Objects.equals(resources.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该任务不可操作");
        }
        if (!CronExpression.isValidExpression(resources.getCronExpression()))
        {
            throw BadRequestException.newExceptionBySystem("cron表达式格式错误");
        }
        resources = schedulerJobRepository.save(resources);
        jobExecutor.updateJobCron(resources);
    }

    /**
     * del
     *
     * @param schedulerJob
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void delete(SchedulerJob schedulerJob)
    {
        if (Objects.equals(schedulerJob.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该任务不可操作");
        }
        jobExecutor.deleteJob(schedulerJob);
        schedulerJobRepository.delete(schedulerJob);
    }

    /**
     * findById
     *
     * @param id
     *
     * @return
     */
    @Override
    @Cacheable(key = "#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public SchedulerJob findById(Long id)
    {
        Optional<SchedulerJob> schedulerJob = schedulerJobRepository.findById(id);
        ValidationUtils.isNull(schedulerJob, "SchedulerJob", "id", id);
        return schedulerJob.get();
    }

    /**
     * 更改定时任务状态
     *
     * @param schedulerJob
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void updatePaused(SchedulerJob schedulerJob)
    {
        if (Objects.equals(schedulerJob.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该任务不可操作");
        }
        if (schedulerJob.getPaused())
        {
            jobExecutor.resumeJob(schedulerJob);
            schedulerJob.setPaused(false);
        }
        else
        {
            jobExecutor.pauseJob(schedulerJob);
            schedulerJob.setPaused(true);
        }
        schedulerJobRepository.save(schedulerJob);
    }

    /**
     * 立即执行定时任务
     *
     * @param schedulerJob
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public void execution(SchedulerJob schedulerJob)
    {
        if (Objects.equals(schedulerJob.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该任务不可操作");
        }
        jobExecutor.runAJobNow(schedulerJob);
    }

    /**
     * 分页查询
     *
     * @param schedulerJob
     * @param pageable
     *
     * @return
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public Map<String, Object> queryAll(SchedulerJob schedulerJob, Pageable pageable)
    {
        return PageUtils.toPage(schedulerJobRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayListWithCapacity(1);
            if (StringUtils.isNotBlank(schedulerJob.getJobName()))
            {
                predicateList.add(criteriaBuilder.like(root.get("jobName").as(String.class), "%" + schedulerJob.getJobName() + "%"));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable));
    }
}
