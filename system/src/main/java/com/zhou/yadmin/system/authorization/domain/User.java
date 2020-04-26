/**
 * <p>
 * 文件名称:    User
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import com.zhou.yadmin.common.domain.BaseEntity;
import org.hibernate.annotations.CreationTimestamp;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:13
 */
@Entity
@Table(name = "t_user")
public class User extends BaseEntity<Long>
{
    private static final long serialVersionUID = -2658337450624750975L;

    @NotBlank
    private String username;

    private String avatar;

    @NotBlank
    @Pattern(regexp = "^[a-z]([a-z0-9]*[-_|\\.]?[a-z0-9]+)*@([a-z0-9]*[-_]?[a-z0-9]+)+[\\.][a-z]{2,3}([\\.][a-z]{2})?$", message = "格式错误")
    private String email;

    @NotNull
    private Boolean enabled;

    private String password;

    @CreationTimestamp
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "last_password_reset_time")
    private Date lastPasswordResetTime;

    @ManyToMany
    @JoinTable(name = "t_user_role", joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

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

    @Override
    public String toString()
    {
        return "User{" + "id=" + getId() + ", username='" + username + '\'' + ", avatar='" + avatar + '\'' + ", email='" + email + '\'' +
               ", enabled=" + enabled + ", password='" + password + '\'' + ", createTime=" + createTime + ", lastPasswordResetTime=" +
               lastPasswordResetTime + '}';
    }
}
