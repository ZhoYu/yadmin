/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.monitor.service.LoggingService
 * </p>
 */
package com.zhou.example.yadmin.logging.service;

import com.zhou.example.yadmin.logging.domain.Logging;
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
     * 分页查询所有的日志对象
     * @param logging
     * @param pageable
     * @return
     */
    Page<Logging> queryAll(Logging logging, Pageable pageable);
}
