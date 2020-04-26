/**
 * <p>
 * 文件名称:    Menu
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

import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 菜单
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:16
 */
@Entity
@Table(name = "t_menu")
public class Menu extends BaseEntity<Long>
{
    private static final long serialVersionUID = 2488701911928220681L;

    @NotBlank
    private String name;

    @Column(unique = true)
    private Long sort;

    @Column(name = "path")
    private String path;

    private String component;

    private String icon;

    /**
     * 上级菜单ID
     */
    @Column(name = "pid", nullable = false)
    private Long pid;

    /**
     * 是否为外链 true/false
     */
    private Boolean iframe;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @ManyToMany
    @JoinTable(name = "t_menu_role", joinColumns = {@JoinColumn(name = "menu_id",referencedColumnName = "id")}, inverseJoinColumns = {@JoinColumn(name = "role_id",referencedColumnName = "id")})
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
     * Gets the value of sort
     *
     * @return the value of sort
     */
    public Long getSort()
    {
        return sort;
    }

    /**
     * Sets the sort
     * <p>You can use getSort() to get the value of sort</p>
     *
     * @param sort sort
     */
    public void setSort(Long sort)
    {
        this.sort = sort;
    }

    /**
     * Gets the value of path
     *
     * @return the value of path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Sets the path
     * <p>You can use getPath() to get the value of path</p>
     *
     * @param path path
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Gets the value of component
     *
     * @return the value of component
     */
    public String getComponent()
    {
        return component;
    }

    /**
     * Sets the component
     * <p>You can use getComponent() to get the value of component</p>
     *
     * @param component component
     */
    public void setComponent(String component)
    {
        this.component = component;
    }

    /**
     * Gets the value of icon
     *
     * @return the value of icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * Sets the icon
     * <p>You can use getIcon() to get the value of icon</p>
     *
     * @param icon icon
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
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
     * Gets the value of iFrame
     *
     * @return the value of iFrame
     */
    public Boolean getiIframe()
    {
        return iframe;
    }

    /**
     * Sets the iFrame
     * <p>You can use getiFrame() to get the value of iFrame</p>
     *
     * @param iFrame iFrame
     */
    public void setiIframe(Boolean iFrame)
    {
        this.iframe = iFrame;
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
}
