/**
 * <p>
 * 文件名称:    Visits
 * </p>
 */
package com.zhou.yadmin.system.monitor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * pv 与 ip 统计
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:42
 */
@Entity
@Table(name = "t_visits")
public class Visits extends BaseEntity<Long>
{
    private static final long serialVersionUID = -2964121289937472041L;

    @Column(unique = true)
    private String date;
    @Column(name = "pv_counts")
    private Long pvCounts;
    @Column(name = "ip_counts")
    private Long ipCounts;
    @Column(name = "week_day")
    private String weekDay;
    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    /**
     * Gets the value of date
     *
     * @return the value of date
     */
    public String getDate()
    {
        return date;
    }

    /**
     * Sets the date
     * <p>You can use getDate() to get the value of date</p>
     *
     * @param date date
     */
    public void setDate(String date)
    {
        this.date = date;
    }

    /**
     * Gets the value of pvCounts
     *
     * @return the value of pvCounts
     */
    public Long getPvCounts()
    {
        return pvCounts;
    }

    /**
     * Sets the pvCounts
     * <p>You can use getPvCounts() to get the value of pvCounts</p>
     *
     * @param pvCounts pvCounts
     */
    public void setPvCounts(Long pvCounts)
    {
        this.pvCounts = pvCounts;
    }

    /**
     * Gets the value of ipCounts
     *
     * @return the value of ipCounts
     */
    public Long getIpCounts()
    {
        return ipCounts;
    }

    /**
     * Sets the ipCounts
     * <p>You can use getIpCounts() to get the value of ipCounts</p>
     *
     * @param ipCounts ipCounts
     */
    public void setIpCounts(Long ipCounts)
    {
        this.ipCounts = ipCounts;
    }

    /**
     * Gets the value of weekDay
     *
     * @return the value of weekDay
     */
    public String getWeekDay()
    {
        return weekDay;
    }

    /**
     * Sets the weekDay
     * <p>You can use getWeekDay() to get the value of weekDay</p>
     *
     * @param weekDay weekDay
     */
    public void setWeekDay(String weekDay)
    {
        this.weekDay = weekDay;
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
