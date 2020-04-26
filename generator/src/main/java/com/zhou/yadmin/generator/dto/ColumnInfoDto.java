/**
 * <p>
 * 文件名称:    ColumnInfoDto
 * </p>
 */
package com.zhou.yadmin.generator.dto;

import java.io.Serializable;

/**
 * <p>
 * 列的数据信息
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 20:21
 */
public class ColumnInfoDto implements Serializable
{
    private static final long serialVersionUID = 7208112490395935739L;

    /**
     * 数据库字段名称
     **/
    private Object columnName;

    /**
     * 允许空值
     **/
    private Object isNullable;

    /**
     * 数据库字段类型
     **/
    private Object columnType;

    /**
     * 数据库字段注释
     **/
    private Object columnComment;

    /**
     * 数据库字段键类型
     **/
    private Object columnKey;

    /**
     * 查询 1:模糊 2：精确
     **/
    private String columnQuery;

    /**
     * 是否在列表显示
     **/
    private String columnShow;

    public ColumnInfoDto()
    {
    }

    public ColumnInfoDto(Object columnName, Object isNullable, Object columnType, Object columnComment, Object columnKey, String columnQuery,
      String columnShow)
    {
        this.columnName = columnName;
        this.isNullable = isNullable;
        this.columnType = columnType;
        this.columnComment = columnComment;
        this.columnKey = columnKey;
        this.columnQuery = columnQuery;
        this.columnShow = columnShow;
    }

    /**
     * Gets the value of columnName
     *
     * @return the value of columnName
     */
    public Object getColumnName()
    {
        return columnName;
    }

    /**
     * Sets the columnName
     * <p>You can use getColumnName() to get the value of columnName</p>
     *
     * @param columnName columnName
     */
    public void setColumnName(Object columnName)
    {
        this.columnName = columnName;
    }

    /**
     * Gets the value of isNullable
     *
     * @return the value of isNullable
     */
    public Object getIsNullable()
    {
        return isNullable;
    }

    /**
     * Sets the isNullable
     * <p>You can use getIsNullable() to get the value of isNullable</p>
     *
     * @param isNullable isNullable
     */
    public void setIsNullable(Object isNullable)
    {
        this.isNullable = isNullable;
    }

    /**
     * Gets the value of columnType
     *
     * @return the value of columnType
     */
    public Object getColumnType()
    {
        return columnType;
    }

    /**
     * Sets the columnType
     * <p>You can use getColumnType() to get the value of columnType</p>
     *
     * @param columnType columnType
     */
    public void setColumnType(Object columnType)
    {
        this.columnType = columnType;
    }

    /**
     * Gets the value of columnComment
     *
     * @return the value of columnComment
     */
    public Object getColumnComment()
    {
        return columnComment;
    }

    /**
     * Sets the columnComment
     * <p>You can use getColumnComment() to get the value of columnComment</p>
     *
     * @param columnComment columnComment
     */
    public void setColumnComment(Object columnComment)
    {
        this.columnComment = columnComment;
    }

    /**
     * Gets the value of columnKey
     *
     * @return the value of columnKey
     */
    public Object getColumnKey()
    {
        return columnKey;
    }

    /**
     * Sets the columnKey
     * <p>You can use getColumnKey() to get the value of columnKey</p>
     *
     * @param columnKey columnKey
     */
    public void setColumnKey(Object columnKey)
    {
        this.columnKey = columnKey;
    }

    /**
     * Gets the value of columnQuery
     *
     * @return the value of columnQuery
     */
    public String getColumnQuery()
    {
        return columnQuery;
    }

    /**
     * Sets the columnQuery
     * <p>You can use getColumnQuery() to get the value of columnQuery</p>
     *
     * @param columnQuery columnQuery
     */
    public void setColumnQuery(String columnQuery)
    {
        this.columnQuery = columnQuery;
    }

    /**
     * Gets the value of columnShow
     *
     * @return the value of columnShow
     */
    public String getColumnShow()
    {
        return columnShow;
    }

    /**
     * Sets the columnShow
     * <p>You can use getColumnShow() to get the value of columnShow</p>
     *
     * @param columnShow columnShow
     */
    public void setColumnShow(String columnShow)
    {
        this.columnShow = columnShow;
    }
}
