/**
 * <p>
 * 文件名称:    SchedulerLogService
 * </p>
 */
package com.zhou.yadmin.system.scheduling.service;

import java.util.Map;

import com.zhou.yadmin.system.scheduling.domain.SchedulerLog;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/30 19:57
 */
public interface SchedulerLogService
{
    /**
     * 模糊分页查询
     * @param schedulerLog
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(SchedulerLog schedulerLog, Pageable pageable);
}
