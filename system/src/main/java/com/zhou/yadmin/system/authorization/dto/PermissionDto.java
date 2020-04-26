/**
 * <p>
 * 文件名称:    PermissionDto
 * </p>
 */
package com.zhou.yadmin.system.authorization.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:54
 */
public class PermissionDto implements Serializable
{
    private static final long serialVersionUID = 4204500506351161181L;

    private Long id;

    private String name;

    private Long pid;

    private String alias;

    private LocalDateTime createTime;

    private List<PermissionDto> children;

    /**
     * Gets the value of id
     *
     * @return the value of id
     */
    public Long getId()
    {
        return id;
    }

    /**
     * Sets the id
     * <p>You can use getId() to get the value of id</p>
     *
     * @param id id
     */
    public void setId(Long id)
    {
        this.id = id;
    }

    /**
     * Gets the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name
     * <p>You can use getName() to get the value of name</p>
     *
     * @param name name
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Gets the value of pid
     *
     * @return the value of pid
     */
    public Long getPid()
    {
        return pid;
    }

    /**
     * Sets the pid
     * <p>You can use getPid() to get the value of pid</p>
     *
     * @param pid pid
     */
    public void setPid(Long pid)
    {
        this.pid = pid;
    }

    /**
     * Gets the value of alias
     *
     * @return the value of alias
     */
    public String getAlias()
    {
        return alias;
    }

    /**
     * Sets the alias
     * <p>You can use getAlias() to get the value of alias</p>
     *
     * @param alias alias
     */
    public void setAlias(String alias)
    {
        this.alias = alias;
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

    /**
     * Gets the value of children
     *
     * @return the value of children
     */
    public List<PermissionDto> getChildren()
    {
        return children;
    }

    /**
     * Sets the children
     * <p>You can use getChildren() to get the value of children</p>
     *
     * @param children children
     */
    public void setChildren(List<PermissionDto> children)
    {
        this.children = children;
    }

    @Override
    public String toString()
    {
        return "Permission{" + "id=" + id + ", name='" + name + '\'' + ", pid=" + pid + ", alias='" + alias + '\'' + ", createTime=" + createTime +
               '}';
    }
}
