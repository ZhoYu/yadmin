/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.monitor.service.LoggingService
 * </p>
 */
package com.zhou.yadmin.logging.service;

import java.time.LocalDate;

import com.zhou.yadmin.logging.domain.Logging;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:36
 */
public interface LoggingService
{
    /**
     * 新增日志
     * @param logging
     */
    @Async
    void save(Logging logging);

    /**
     * 统计时间段内的请求ip
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    Long countRequestIpByDate(LocalDate startDate, LocalDate endDate);

    /**
     * 分页查询所有的日志对象
     * @param logging
     * @param pageable
     * @return
     */
    Page<Logging> queryAll(Logging logging, Pageable pageable);
}
