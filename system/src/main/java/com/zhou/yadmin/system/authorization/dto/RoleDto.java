/**
 * <p>
 * 文件名称:    RoleDto
 * </p>
 */
package com.zhou.yadmin.system.authorization.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.google.common.base.MoreObjects;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:52
 */
public class RoleDto implements Serializable
{
    private static final long serialVersionUID = -3606394358605599718L;

    private Long id;

    private String name;

    private String remark;

    private Set<PermissionDto> permissions;

    private LocalDateTime createTime;

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
     * Gets the value of permissions
     *
     * @return the value of permissions
     */
    public Set<PermissionDto> getPermissions()
    {
        return permissions;
    }

    /**
     * Sets the permissions
     * <p>You can use getPermissions() to get the value of permissions</p>
     *
     * @param permissions permissions
     */
    public void setPermissions(Set<PermissionDto> permissions)
    {
        this.permissions = permissions;
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

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("remark", remark).add("permissions", permissions)
          .add("createTime", createTime).toString();
    }
}
