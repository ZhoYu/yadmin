/**
 * <p>
 * 文件名称:    SchedulerLog
 * </p>
 */
package com.zhou.yadmin.system.scheduling.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 定时调度任务 日志
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 20:36
 */
@Entity
@Table(name = "t_scheduler_log")
public class SchedulerLog extends BaseEntity<Long>
{
    private static final long serialVersionUID = 1894495810933439932L;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * Bean名称
     */
    @Column(name = "bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "success")
    private Boolean success;

    /**
     * 异常详细
     */
    @Column(name = "exception_detail", columnDefinition = "text")
    private String exceptionDetail;

    /**
     * 耗时（毫秒）
     */
    private Long time;

    /**
     * 创建日期
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * Gets the value of jobName
     *
     * @return the value of jobName
     */
    public String getJobName()
    {
        return jobName;
    }

    /**
     * Sets the jobName
     * <p>You can use getJobName() to get the value of jobName</p>
     *
     * @param jobName jobName
     */
    public void setJobName(String jobName)
    {
        this.jobName = jobName;
    }

    /**
     * Gets the value of beanName
     *
     * @return the value of beanName
     */
    public String getBeanName()
    {
        return beanName;
    }

    /**
     * Sets the beanName
     * <p>You can use getBeanName() to get the value of beanName</p>
     *
     * @param beanName beanName
     */
    public void setBeanName(String beanName)
    {
        this.beanName = beanName;
    }

    /**
     * Gets the value of methodName
     *
     * @return the value of methodName
     */
    public String getMethodName()
    {
        return methodName;
    }

    /**
     * Sets the methodName
     * <p>You can use getMethodName() to get the value of methodName</p>
     *
     * @param methodName methodName
     */
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }

    /**
     * Gets the value of params
     *
     * @return the value of params
     */
    public String getParams()
    {
        return params;
    }

    /**
     * Sets the params
     * <p>You can use getParams() to get the value of params</p>
     *
     * @param params params
     */
    public void setParams(String params)
    {
        this.params = params;
    }

    /**
     * Gets the value of cronExpression
     *
     * @return the value of cronExpression
     */
    public String getCronExpression()
    {
        return cronExpression;
    }

    /**
     * Sets the cronExpression
     * <p>You can use getCronExpression() to get the value of cronExpression</p>
     *
     * @param cronExpression cronExpression
     */
    public void setCronExpression(String cronExpression)
    {
        this.cronExpression = cronExpression;
    }

    /**
     * Gets the value of success
     *
     * @return the value of success
     */
    public Boolean getSuccess()
    {
        return success;
    }

    /**
     * Sets the success
     * <p>You can use getSuccess() to get the value of success</p>
     *
     * @param success success
     */
    public void setSuccess(Boolean success)
    {
        this.success = success;
    }

    /**
     * Gets the value of exceptionDetail
     *
     * @return the value of exceptionDetail
     */
    public String getExceptionDetail()
    {
        return exceptionDetail;
    }

    /**
     * Sets the exceptionDetail
     * <p>You can use getExceptionDetail() to get the value of exceptionDetail</p>
     *
     * @param exceptionDetail exceptionDetail
     */
    public void setExceptionDetail(String exceptionDetail)
    {
        this.exceptionDetail = exceptionDetail;
    }

    /**
     * Gets the value of time
     *
     * @return the value of time
     */
    public Long getTime()
    {
        return time;
    }

    /**
     * Sets the time
     * <p>You can use getTime() to get the value of time</p>
     *
     * @param time time
     */
    public void setTime(Long time)
    {
        this.time = time;
    }

    /**
     * Gets the value of createTime
     *
     * @return the value of createTime
     */
    public LocalDateTime getCreateTime()
    {
        return createTime;
    }

    /**
     * Sets the createTime
     * <p>You can use getCreateTime() to get the value of createTime</p>
     *
     * @param createTime createTime
     */
    public void setCreateTime(LocalDateTime createTime)
    {
        this.createTime = createTime;
    }
}
