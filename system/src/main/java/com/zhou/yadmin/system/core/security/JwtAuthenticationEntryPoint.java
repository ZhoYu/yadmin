/**
 * <p>
 * 文件名称:    JwtAuthenticationEntryPoint
 * </p>
 */
package com.zhou.yadmin.system.core.security;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:02
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable
{
    private static final long serialVersionUID = 5463771142725095213L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException
    {
        /**
         * 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送 401 响应
         */
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException == null ? "Unauthorized" : authException.getMessage());
    }
}
