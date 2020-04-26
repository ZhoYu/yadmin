/**
 * <p>
 * 文件名称:    Role
 * </p>
 */
package com.zhou.yadmin.system.authorization.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:15
 */
@Entity
@Table(name = "t_role")
public class Role extends BaseEntity<Long>
{
    private static final long serialVersionUID = -7667210149216599263L;

    @Column(nullable = false)
    @NotBlank
    private String name;

    @Column
    private String remark;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(name = "t_role_permission", joinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "permission_id", referencedColumnName = "id")})
    private Set<Permission> permissions;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<Menu> menus;

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
     * Gets the value of users
     *
     * @return the value of users
     */
    public Set<User> getUsers()
    {
        return users;
    }

    /**
     * Sets the users
     * <p>You can use getUsers() to get the value of users</p>
     *
     * @param users users
     */
    public void setUsers(Set<User> users)
    {
        this.users = users;
    }

    /**
     * Gets the value of permissions
     *
     * @return the value of permissions
     */
    public Set<Permission> getPermissions()
    {
        return permissions;
    }

    /**
     * Sets the permissions
     * <p>You can use getPermissions() to get the value of permissions</p>
     *
     * @param permissions permissions
     */
    public void setPermissions(Set<Permission> permissions)
    {
        this.permissions = permissions;
    }

    /**
     * Gets the value of menus
     *
     * @return the value of menus
     */
    public Set<Menu> getMenus()
    {
        return menus;
    }

    /**
     * Sets the menus
     * <p>You can use getMenus() to get the value of menus</p>
     *
     * @param menus menus
     */
    public void setMenus(Set<Menu> menus)
    {
        this.menus = menus;
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
        return "Role{id=" + getId() + ", name='" + name + '\'' + ", remark='" + remark + '\'' + ", createDateTime=" + createTime + '}';
    }
}
