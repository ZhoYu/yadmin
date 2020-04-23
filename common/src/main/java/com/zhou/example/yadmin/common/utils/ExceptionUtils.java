/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.utils.ExceptionUtils
 * </p>
 */
package com.zhou.example.yadmin.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * 异常工具
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/26 19:06
 */
public class ExceptionUtils
{
    /**
     * 获取堆栈信息
     *
     * @param throwable
     *
     * @return
     */
    public static String getStackTrace(Throwable throwable)
    {
        StringWriter sw = new StringWriter();
        try (PrintWriter pw = new PrintWriter(sw))
        {
            throwable.printStackTrace(pw);
            return "\n" + sw.toString();
        }
    }
}
