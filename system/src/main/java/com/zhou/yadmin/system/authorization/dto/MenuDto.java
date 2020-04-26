/**
 * <p>
 * 文件名称:    MenuDto
 * </p>
 */
package com.zhou.yadmin.system.authorization.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * <p>
 * 构建前端路由时用到
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:28
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MenuDto implements Serializable
{
    private static final long serialVersionUID = -4735667634383404904L;

    private Long id;

    private String name;

    private Long sort;

    private String path;

    private String redirect;

    private String component;

    private Long pid;

    private Boolean iframe;

    private String icon;

    private Set<RoleDto> roles;

    private LocalDateTime createTime;

    private Boolean alwaysShow;

    private MenuMetaDto meta;

    private List<MenuDto> children;

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
     * Gets the value of redirect
     *
     * @return the value of redirect
     */
    public String getRedirect()
    {
        return redirect;
    }

    /**
     * Sets the redirect
     * <p>You can use getRedirect() to get the value of redirect</p>
     *
     * @param redirect redirect
     */
    public void setRedirect(String redirect)
    {
        this.redirect = redirect;
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
    public Boolean getIframe()
    {
        return iframe;
    }

    /**
     * Sets the iFrame
     * <p>You can use getiFrame() to get the value of iFrame</p>
     *
     * @param iframe iFrame
     */
    public void setIframe(Boolean iframe)
    {
        this.iframe = iframe;
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
     * Gets the value of roles
     *
     * @return the value of roles
     */
    public Set<RoleDto> getRoles()
    {
        return roles;
    }

    /**
     * Sets the roles
     * <p>You can use getRoles() to get the value of roles</p>
     *
     * @param roles roles
     */
    public void setRoles(Set<RoleDto> roles)
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

    /**
     * Gets the value of alwaysShow
     *
     * @return the value of alwaysShow
     */
    public Boolean getAlwaysShow()
    {
        return alwaysShow;
    }

    /**
     * Sets the alwaysShow
     * <p>You can use getAlwaysShow() to get the value of alwaysShow</p>
     *
     * @param alwaysShow alwaysShow
     */
    public void setAlwaysShow(Boolean alwaysShow)
    {
        this.alwaysShow = alwaysShow;
    }

    /**
     * Gets the value of meta
     *
     * @return the value of meta
     */
    public MenuMetaDto getMeta()
    {
        return meta;
    }

    /**
     * Sets the meta
     * <p>You can use getMeta() to get the value of meta</p>
     *
     * @param meta meta
     */
    public void setMeta(MenuMetaDto meta)
    {
        this.meta = meta;
    }

    /**
     * Gets the value of children
     *
     * @return the value of children
     */
    public List<MenuDto> getChildren()
    {
        return children;
    }

    /**
     * Sets the children
     * <p>You can use getChildren() to get the value of children</p>
     *
     * @param children children
     */
    public void setChildren(List<MenuDto> children)
    {
        this.children = children;
    }
}
