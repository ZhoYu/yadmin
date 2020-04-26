/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.scheduling.task.VisitsScheduling
 * </p>
 */
package com.zhou.yadmin.system.scheduling.task;

import com.zhou.yadmin.system.monitor.service.VisitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/10 19:52
 */
@Component
public class VisitsTask
{
    @Autowired
    private VisitsService visitsService;

    /**
     * 每天0：05运行
     */
    public void run()
    {
        visitsService.save();
    }
}
