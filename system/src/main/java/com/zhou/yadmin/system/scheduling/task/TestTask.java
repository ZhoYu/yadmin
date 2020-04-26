/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.scheduling.task.TestTask
 * </p>
 */
package com.zhou.yadmin.system.scheduling.task;

import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/27 0:50
 */
@Component
public class TestTask extends AbstractBaseComponent
{
    public void run()
    {
        logger.info("执行成功");
    }

    public void run1(String str)
    {
        logger.info("执行成功，参数为： {}", str);
    }
}
