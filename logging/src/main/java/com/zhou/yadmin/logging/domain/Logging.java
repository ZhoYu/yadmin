/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.monitor.domain.Logging
 * </p>
 */
package com.zhou.yadmin.logging.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 请求日志
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 23:14
 */
@Entity
@Table(name = "t_log")
public class Logging extends BaseEntity<Long>
{
    private static final long serialVersionUID = -1394297751630079910L;
    /**
     * 操作用户
     */
    private String username;

    /**
     * 描述
     */
    private String description;

    /**
     * 方法名
     */
    private String method;

    /**
     * 参数
     */
    @Column(columnDefinition = "text")
    private String params;

    /**
     * 日志类型
     */
    @Column(name = "log_type")
    private String logType;

    /**
     * 请求ip
     */
    @Column(name = "request_ip")
    private String requestIp;

    /**
     * 请求耗时
     */
    private Long time;

    /**
     * 异常详细
     */
    @Column(name = "exception_detail", columnDefinition = "text")
    private String exceptionDetail;

    /**
     * 创建日期
     */
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    public Logging()
    {
    }

    public Logging(String logType, Long time)
    {
        this.logType = logType;
        this.time = time;
    }

    /**
     * Gets the value of username
     *
     * @return the value of username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username
     * <p>You can use getUsername() to get the value of username</p>
     *
     * @param username username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Gets the value of description
     *
     * @return the value of description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * Sets the description
     * <p>You can use getDescription() to get the value of description</p>
     *
     * @param description description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * Gets the value of method
     *
     * @return the value of method
     */
    public String getMethod()
    {
        return method;
    }

    /**
     * Sets the method
     * <p>You can use getMethod() to get the value of method</p>
     *
     * @param method method
     */
    public void setMethod(String method)
    {
        this.method = method;
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
     * Gets the value of logType
     *
     * @return the value of logType
     */
    public String getLogType()
    {
        return logType;
    }

    /**
     * Sets the logType
     * <p>You can use getLogType() to get the value of logType</p>
     *
     * @param logType logType
     */
    public void setLogType(String logType)
    {
        this.logType = logType;
    }

    /**
     * Gets the value of requestIp
     *
     * @return the value of requestIp
     */
    public String getRequestIp()
    {
        return requestIp;
    }

    /**
     * Sets the requestIp
     * <p>You can use getRequestIp() to get the value of requestIp</p>
     *
     * @param requestIp requestIp
     */
    public void setRequestIp(String requestIp)
    {
        this.requestIp = requestIp;
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
