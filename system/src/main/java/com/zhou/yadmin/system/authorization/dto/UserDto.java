/**
 * <p>
 * 文件名称:    UserDto
 * </p>
 */
package com.zhou.yadmin.system.authorization.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:55
 */
public class UserDto implements Serializable
{
    private static final long serialVersionUID = -9019252490253683356L;

    private Long id;

    private String username;

    private String avatar;

    private String email;

    private Boolean enabled;

    @JsonIgnore
    private String password;

    private LocalDateTime createTime;

    private Date lastPasswordResetTime;

    private Set<RoleDto> roles;

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
     * Gets the value of username
     *
     * @return the value of username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets the username
     * <p>You can use getUsername() to get the value of username</p>
     *
     * @param username username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Gets the value of avatar
     *
     * @return the value of avatar
     */
    public String getAvatar()
    {
        return avatar;
    }

    /**
     * Sets the avatar
     * <p>You can use getAvatar() to get the value of avatar</p>
     *
     * @param avatar avatar
     */
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    /**
     * Gets the value of email
     *
     * @return the value of email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email
     * <p>You can use getEmail() to get the value of email</p>
     *
     * @param email email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets the value of enabled
     *
     * @return the value of enabled
     */
    public Boolean getEnabled()
    {
        return enabled;
    }

    /**
     * Sets the enabled
     * <p>You can use getEnabled() to get the value of enabled</p>
     *
     * @param enabled enabled
     */
    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * Gets the value of password
     *
     * @return the value of password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets the password
     * <p>You can use getPassword() to get the value of password</p>
     *
     * @param password password
     */
    public void setPassword(String password)
    {
        this.password = password;
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
     * Gets the value of lastPasswordResetTime
     *
     * @return the value of lastPasswordResetTime
     */
    public Date getLastPasswordResetTime()
    {
        return lastPasswordResetTime;
    }

    /**
     * Sets the lastPasswordResetTime
     * <p>You can use getLastPasswordResetTime() to get the value of lastPasswordResetTime</p>
     *
     * @param lastPasswordResetTime lastPasswordResetTime
     */
    public void setLastPasswordResetTime(Date lastPasswordResetTime)
    {
        this.lastPasswordResetTime = lastPasswordResetTime;
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

    @Override
    public String toString()
    {
        return MoreObjects.toStringHelper(this).add("id", id).add("username", username).add("avatar", avatar).add("email", email)
          .add("enabled", enabled).add("password", password).add("createTime", createTime).add("lastPasswordResetTime", lastPasswordResetTime)
          .add("roles", roles).toString();
    }
}
