/**
 * <p>
 * 文件名称:    StringUtils
 * </p>
 */
package com.zhou.yadmin.common.utils;

import java.util.UUID;

import com.zhou.yadmin.common.constants.CommonConstant;

/**
 * <p>
 * 字符串工具类
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/22 20:09
 */
public class StringUtils
{
    /**
     * 获取随机UUID
     *
     * @return 随机UUID
     */
    public static String uuid()
    {
        return UUID.randomUUID().toString().replace(CommonConstant.DASH_DELIMITER, org.apache.commons.lang3.StringUtils.EMPTY);
    }
}
