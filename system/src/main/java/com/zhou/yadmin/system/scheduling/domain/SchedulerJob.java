/**
 * <p>
 * 文件名称:    SchedulerJob
 * </p>
 */
package com.zhou.yadmin.system.scheduling.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

import com.google.common.base.MoreObjects;
import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * <p>
 * 定时调度任务
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/27 20:25
 */
@Entity
@Table(name = "t_scheduler_job")
public class SchedulerJob extends BaseEntity<Long>
{
    private static final long serialVersionUID = 7904035398297345815L;

    /**
     * 定时器名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * Bean名称
     */
    @NotBlank
    @Column(name = "bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @NotBlank
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
    @NotBlank
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "paused")
    private Boolean paused = false;

    /**
     * 备注
     */
    @NotBlank
    @Column(name = "remark")
    private String remark;

    /**
     * 创建日期
     */
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

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
     * Gets the value of paused
     *
     * @return the value of paused
     */
    public Boolean getPaused()
    {
        return paused;
    }

    /**
     * Sets the paused
     * <p>You can use getPaused() to get the value of paused</p>
     *
     * @param paused paused
     */
    public void setPaused(Boolean paused)
    {
        this.paused = paused;
    }

    /**
     * Gets the value of remark
     *
     * @return the value of remark
     */
    public String getRemark()
    {
        return remark;
    }

    /**
     * Sets the remark
     * <p>You can use getRemark() to get the value of remark</p>
     *
     * @param remark remark
     */
    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    /**
     * Gets the value of updateTime
     *
     * @return the value of updateTime
     */
    public LocalDateTime getUpdateTime()
    {
        return updateTime;
    }

    /**
     * Sets the updateTime
     * <p>You can use getUpdateTime() to get the value of updateTime</p>
     *
     * @param updateTime updateTime
     */
    public void setUpdateTime(LocalDateTime updateTime)
    {
        this.updateTime = updateTime;
    }

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("jobName", jobName).add("beanName", beanName).add("methodName", methodName).add("params", params)
          .add("cronExpression", cronExpression).add("paused", paused).add("remark", remark).add("updateTime", updateTime).toString();
    }
}
