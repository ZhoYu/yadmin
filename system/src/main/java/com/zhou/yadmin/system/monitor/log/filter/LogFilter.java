/**
 * <p>
 * 文件名称:    LogFilter
 * </p>
 */
package com.zhou.yadmin.system.monitor.log.filter;

import java.text.DateFormat;
import java.util.Date;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;
import com.zhou.yadmin.system.monitor.dto.LogMessage;
import com.zhou.yadmin.system.monitor.log.queue.LoggerQueue;

/**
 * <p>
 * 定义Logfilter拦截输出日志
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/10 20:01
 */
public class LogFilter extends Filter<ILoggingEvent>
{
    private Level level;

    /**
     * Gets the value of level
     *
     * @return the value of level
     */
    public Level getLevel()
    {
        return level;
    }

    /**
     * Sets the level
     * <p>You can use getLevel() to get the value of level</p>
     *
     * @param level level
     */
    public void setLevel(Level level)
    {
        this.level = level;
    }

    @Override
    public FilterReply decide(ILoggingEvent event)
    {
        if (event.getLevel().isGreaterOrEqual(level))
        {
            LogMessage loggerMessage =
              new LogMessage(event.getFormattedMessage(), DateFormat.getDateTimeInstance().format(new Date(event.getTimeStamp())),
                event.getThreadName(), event.getLoggerName(), event.getLevel().levelStr);
            LoggerQueue.getInstance().push(loggerMessage);
            return FilterReply.ACCEPT;
        }
        return FilterReply.NEUTRAL;
    }
}
