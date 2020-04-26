/**
 * <p>
 * 文件名称:    SchedulerTask
 * </p>
 */
package com.zhou.yadmin.system.scheduling.dto;

import java.lang.reflect.Method;

import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.common.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

/**
 * <p>
 * 调度任务
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 21:42
 */
public class SchedulerTask extends AbstractBaseComponent implements Runnable
{
    private Object target;
    private Method method;
    private String params;

    public SchedulerTask(String beanName, String methodName, String params) throws NoSuchMethodException
    {
        this.target = SpringUtils.getBean(beanName);
        this.params = params;
        if (StringUtils.isNotBlank(params))
        {
            this.method = target.getClass().getDeclaredMethod(methodName, String.class);
        }
        else
        {
            this.method = target.getClass().getDeclaredMethod(methodName);
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        try
        {
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params))
            {
                method.invoke(target, params);
            }
            else
            {
                method.invoke(target);
            }
        }
        catch (Exception e)
        {
            logger.error("定时任务执行失败", e);
        }
    }
}
