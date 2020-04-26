/**
 * <p>
 * 文件名称:    Permission
 * </p>
 */
package com.zhou.yadmin.system.authorization.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 权限许可
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:17
 */
@Entity
@Table(name = "t_permission")
public class Permission extends BaseEntity<Long>
{
    private static final long serialVersionUID = -9219074209066012714L;

    @NotBlank
    private String name;

    /**
     * 上级类目
     */
    @NotNull
    @Column(name = "pid", nullable = false)
    private Long pid;

    @NotBlank
    private String alias;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles;

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
     * Gets the value of roles
     *
     * @return the value of roles
     */
    public Set<Role> getRoles()
    {
        return roles;
    }

    /**
     * Sets the roles
     * <p>You can use getRoles() to get the value of roles</p>
     *
     * @param roles roles
     */
    public void setRoles(Set<Role> roles)
    {
        this.roles = roles;
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
        return "Permission{" + "id=" + getId() + ", name='" + name + '\'' + ", pid=" + pid + ", alias='" + alias + '\'' + ", createTime=" +
               createTime + '}';
    }
}
