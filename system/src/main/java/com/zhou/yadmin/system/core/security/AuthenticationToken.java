/**
 * <p>
 * 文件名称:    AuthenticationToken
 * </p>
 */
package com.zhou.yadmin.system.core.security;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:01
 */
public class AuthenticationToken implements Serializable
{
    private final String token;

    public AuthenticationToken(String token)
    {
        this.token = token;
    }

    /**
     * Gets the value of token
     *
     * @return the value of token
     */
    public String getToken()
    {
        return token;
    }
}
