/**
 * <p>
 * 文件名称:    VisitsService
 * </p>
 */
package com.zhou.yadmin.system.monitor.service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.Map;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:35
 */
public interface VisitsService
{
    /**
     * 提供给定时任务，每天0点执行
     */
    void save();
    /**
     * 新增记录
     *
     * @param request
     */
    void count(HttpServletRequest request);

    /**
     * 获取数据
     *
     * @return
     */
    Map<String, Object> get();

    /**
     * 根据时间获取一段时间的数据
     *
     * @param localDate
     *
     * @return
     */
    Map<String, Object> list(LocalDate localDate);

    /**
     * getChartData
     *
     * @return
     */
    Map<String, Object> getChartData();
}
