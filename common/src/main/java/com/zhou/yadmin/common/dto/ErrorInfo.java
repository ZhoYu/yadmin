/**
 * <p>
 * 文件名称:    ErrorInfo
 * </p>
 */
package com.zhou.yadmin.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zhou.yadmin.common.constants.DateConstant;

/**
 * <p>
 * 异常信息
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:58
 */
public class ErrorInfo implements Serializable
{
    private static final long serialVersionUID = -6051971605912852100L;

    private Integer status;
    @JsonFormat(pattern = DateConstant.DATE_TIME_PATTERN)
    private LocalDateTime timestamp;
    private String message;

    public ErrorInfo()
    {
        timestamp = LocalDateTime.now();
    }

    public ErrorInfo(Integer status, String message)
    {
        this();
        this.status = status;
        this.message = message;
    }

    /**
     * Gets the value of status
     *
     * @return the value of status
     */
    public Integer getStatus()
    {
        return status;
    }

    /**
     * Sets the status
     * <p>You can use getStatus() to get the value of status</p>
     *
     * @param status status
     */
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    /**
     * Gets the value of timestamp
     *
     * @return the value of timestamp
     */
    public LocalDateTime getTimestamp()
    {
        return timestamp;
    }

    /**
     * Sets the timestamp
     * <p>You can use getTimestamp() to get the value of timestamp</p>
     *
     * @param timestamp timestamp
     */
    public void setTimestamp(LocalDateTime timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Gets the value of message
     *
     * @return the value of message
     */
    public String getMessage()
    {
        return message;
    }

    /**
     * Sets the message
     * <p>You can use getMessage() to get the value of message</p>
     *
     * @param message message
     */
    public void setMessage(String message)
    {
        this.message = message;
    }
}
