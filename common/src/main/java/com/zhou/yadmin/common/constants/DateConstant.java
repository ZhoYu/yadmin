/**
 * <p>
 * 文件名称:    CommonConstant
 * </p>
 */
package com.zhou.yadmin.common.constants;

import java.time.format.DateTimeFormatter;

/**
 * <p>
 * 时间日期常量
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 22:08
 */
public class DateConstant
{
    /**
     * 日期格式化字符串
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 日期格式化字符串 斜线分隔符号
     */
    public static final String DATE_SLASH_PATTERN = "yyyy/MM/dd";
    /**
     * 日期格式化字符串
     */
    public static final String DATE_CN_PATTERN = "yyyy年MM月dd日";
    /**
     * 日期格式化字符串 无分隔符号
     */
    public static final String DATE_THIN_PATTERN = "yyyyMMdd";
    /**
     * 时间格式化字符串
     */
    public static final String TIME_PATTERN = "HH:mm:ss";
    /**
     * 时间格式化字符串 无分隔符号
     */
    public static final String TIME_THIN_PATTERN = "HHmmss";
    /**
     * 日期时间格式化字符串
     */
    public static final String DATE_TIME_PATTERN = DATE_PATTERN + ' ' + TIME_PATTERN;
    /**
     * 日期时间格式化字符串
     */
    public static final String DATE_TIME_CN_PATTERN = DATE_CN_PATTERN + ' ' + TIME_PATTERN;
    /**
     * 日期时间格式化字符串
     */
    public static final String DATE_TIME_THIN_PATTERN = DATE_THIN_PATTERN + TIME_THIN_PATTERN;
    /**
     * 时间日期格式化
     */
    public static final DateTimeFormatter FORMATTER_DATE_TIME_THIN = DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_THIN_PATTERN);
}
