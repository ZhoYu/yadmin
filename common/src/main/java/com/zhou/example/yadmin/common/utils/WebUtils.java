/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.utils.WebUtils
 * </p>
 */
package com.zhou.example.yadmin.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zhou.example.yadmin.common.exception.BadRequestException;
import com.zhou.example.yadmin.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * <p>
 * web 工具类
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:27
 */
public final class WebUtils
{
    private static final String STR_UNKNOWN = "unknown";
    /**
     * 本地 ip 地址
     */
    public static final String LOCAL_HOST = "127.0.0.1";

    private WebUtils()
    {
    }

    /**
     * 获取当前线程的请求对象 request
     * 不建议使用, 一般来说request都能通过spring注入来使用, 并能保证线程安全
     * @return
     */
    public static HttpServletRequest getRequest()
    {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取当前线程的响应对象 response
     * 不建议使用, 一般来说request都能通过spring注入来使用, 并能保证线程安全
     * @return
     */
    public static HttpServletResponse getResponse()
    {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }

    /**
     * 获取当前登录的用户名
     * @return
     */
    public static UserDetails getUserDetails() {
        UserDetails userDetails = null;
        try {
            userDetails = (UserDetails) org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            throw new BadRequestException(BaseException.ERROR_CODE_MODULE_COMMON, HttpStatus.UNAUTHORIZED, "登录状态过期");
        }
        return userDetails;
    }

    /**
     * 获取服务请求端(客户端)ip地址
     *
     * @param request
     *
     * @return
     */
    public static String getIpAddress(HttpServletRequest request)
    {
        if (request != null)
        {
            String ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip) || STR_UNKNOWN.equalsIgnoreCase(ip))
            {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || STR_UNKNOWN.equalsIgnoreCase(ip))
            {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (StringUtils.isBlank(ip) || STR_UNKNOWN.equalsIgnoreCase(ip))
            {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || STR_UNKNOWN.equalsIgnoreCase(ip))
            {
                ip = request.getHeader("X-Real-IP");
            }
            if (StringUtils.isBlank(ip) || STR_UNKNOWN.equalsIgnoreCase(ip))
            {
                ip = request.getRemoteAddr();
            }
            return "0:0:0:0:0:0:0:1".equals(ip) ? LOCAL_HOST : ip;
        }
        return STR_UNKNOWN;
    }
}
