/**
 * <p>
 * 文件名称:    JwtUser
 * </p>
 */
package com.zhou.yadmin.system.core.security;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Sets;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 19:28
 */
public class JwtUser implements UserDetails
{
    private static final long serialVersionUID = -8561286483627177637L;

    @JsonIgnore
    private final Long id;

    private final String username;

    @JsonIgnore
    private final String password;

    private final String avatar;

    private final String email;

    @JsonIgnore
    private final Collection<? extends GrantedAuthority> authorities;

    private final boolean enabled;

    private LocalDateTime createTime;

    @JsonIgnore
    private final Date lastPasswordResetDate;

    public JwtUser(Long id, String username, String password, String avatar, String email, Collection<? extends GrantedAuthority> authorities,
      boolean enabled, LocalDateTime createTime, Date lastPasswordResetDate)
    {
        this.id = id;
        this.username = username;
        this.password = password;
        this.avatar = avatar;
        this.email = email;
        this.authorities = authorities;
        this.enabled = enabled;
        this.createTime = createTime;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

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
     * Gets the value of username
     *
     * @return the value of username
     */
    @Override
    public String getUsername()
    {
        return username;
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
     * Gets the value of email
     *
     * @return the value of email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Gets the value of authorities
     *
     * @return the value of authorities
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return authorities;
    }

    /**
     * Gets the value of lastPasswordResetDate
     *
     * @return the value of lastPasswordResetDate
     */
    public Date getLastPasswordResetDate()
    {
        return lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword()
    {
        return password;
    }

    @Override
    public boolean isEnabled()
    {
        return enabled;
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
     * 在我们保存权限的时候加上了前缀ROLE_，因此在这里需要处理下数据
     *
     * @return
     */
    public Collection<String> getRoles()
    {
        Set<String> roles = Sets.newLinkedHashSetWithExpectedSize(authorities.size());
        for (GrantedAuthority authority : authorities)
        {
            roles.add(authority.getAuthority().substring(5));
        }
        return roles;
    }
}
