/**
 * <p>
 * 文件名称:    AuthorizationUser
 * </p>
 */
package com.zhou.yadmin.system.core.security;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 19:57
 */
public class AuthorizationUser
{
    @NotBlank
    private String username;

    @NotBlank
    private String password;

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

    @Override
    public String toString()
    {
        return "{username = " + username + ", password = ******}";
    }
}
