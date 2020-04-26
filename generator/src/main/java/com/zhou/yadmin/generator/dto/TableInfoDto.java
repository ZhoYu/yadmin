/**
 * <p>
 * 文件名称:    TableInfoDto
 * </p>
 */
package com.zhou.yadmin.generator.dto;

import java.io.Serializable;

/**
 * <p>
 * 表的数据信息
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 20:22
 */
public class TableInfoDto implements Serializable
{
    private static final long serialVersionUID = 2997314147637572473L;

    /**
     * 表名称
     **/
    private Object tableName;

    /**
     * 创建日期
     **/
    private Object createTime;

    public TableInfoDto()
    {
    }

    public TableInfoDto(Object tableName, Object createTime)
    {
        this.tableName = tableName;
        this.createTime = createTime;
    }

    /**
     * Gets the value of tableName
     *
     * @return the value of tableName
     */
    public Object getTableName()
    {
        return tableName;
    }

    /**
     * Sets the tableName
     * <p>You can use getTableName() to get the value of tableName</p>
     *
     * @param tableName tableName
     */
    public void setTableName(Object tableName)
    {
        this.tableName = tableName;
    }

    /**
     * Gets the value of createTime
     *
     * @return the value of createTime
     */
    public Object getCreateTime()
    {
        return createTime;
    }

    /**
     * Sets the createTime
     * <p>You can use getCreateTime() to get the value of createTime</p>
     *
     * @param createTime createTime
     */
    public void setCreateTime(Object createTime)
    {
        this.createTime = createTime;
    }
}
