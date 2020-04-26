/**
 * <p>
 * 文件名称:    LogMessage
 * </p>
 */
package com.zhou.yadmin.system.monitor.dto;

import java.io.Serializable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/10 19:47
 */
public class LogMessage implements Serializable
{
    private static final long serialVersionUID = -8158905318770373036L;

    private String body;
    private String timestamp;
    private String threadName;
    private String className;
    private String level;

    public LogMessage(String body, String timestamp, String threadName, String className, String level)
    {
        this.body = body;
        this.timestamp = timestamp;
        this.threadName = threadName;
        this.className = className;
        this.level = level;
    }

    /**
     * Gets the value of body
     *
     * @return the value of body
     */
    public String getBody()
    {
        return body;
    }

    /**
     * Sets the body
     * <p>You can use getBody() to get the value of body</p>
     *
     * @param body body
     */
    public void setBody(String body)
    {
        this.body = body;
    }

    /**
     * Gets the value of timestamp
     *
     * @return the value of timestamp
     */
    public String getTimestamp()
    {
        return timestamp;
    }

    /**
     * Sets the timestamp
     * <p>You can use getTimestamp() to get the value of timestamp</p>
     *
     * @param timestamp timestamp
     */
    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    /**
     * Gets the value of threadName
     *
     * @return the value of threadName
     */
    public String getThreadName()
    {
        return threadName;
    }

    /**
     * Sets the threadName
     * <p>You can use getThreadName() to get the value of threadName</p>
     *
     * @param threadName threadName
     */
    public void setThreadName(String threadName)
    {
        this.threadName = threadName;
    }

    /**
     * Gets the value of className
     *
     * @return the value of className
     */
    public String getClassName()
    {
        return className;
    }

    /**
     * Sets the className
     * <p>You can use getClassName() to get the value of className</p>
     *
     * @param className className
     */
    public void setClassName(String className)
    {
        this.className = className;
    }

    /**
     * Gets the value of level
     *
     * @return the value of level
     */
    public String getLevel()
    {
        return level;
    }

    /**
     * Sets the level
     * <p>You can use getLevel() to get the value of level</p>
     *
     * @param level level
     */
    public void setLevel(String level)
    {
        this.level = level;
    }
}
