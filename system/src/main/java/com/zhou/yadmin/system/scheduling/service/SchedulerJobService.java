/**
 * <p>
 * 文件名称:    SchedulerJobService
 * </p>
 */
package com.zhou.yadmin.system.scheduling.service;

import java.util.Map;

import com.zhou.yadmin.system.scheduling.domain.SchedulerJob;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/30 19:15
 */
public interface SchedulerJobService
{
    /**
     * create
     * @param resources
     * @return
     */
    SchedulerJob create(SchedulerJob resources);

    /**
     * update
     * @param resources
     * @return
     */
    void update(SchedulerJob resources);

    /**
     * del
     * @param schedulerJob
     */
    void delete(SchedulerJob schedulerJob);

    /**
     * findById
     * @param id
     * @return
     */
    SchedulerJob findById(Long id);

    /**
     * 更改定时任务状态
     * @param schedulerJob
     */
    void updatePaused(SchedulerJob schedulerJob);

    /**
     * 立即执行定时任务
     * @param schedulerJob
     */
    void execution(SchedulerJob schedulerJob);

    /**
     * 分页查询
     * @param schedulerJob
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(SchedulerJob schedulerJob, Pageable pageable);
}
