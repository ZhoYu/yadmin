/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.utils.ValidationUtils
 * </p>
 */
package com.zhou.example.yadmin.common.utils;

import java.util.Optional;

import com.zhou.example.yadmin.common.constants.CommonConstant;
import com.zhou.example.yadmin.common.exception.BadRequestException;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:59
 */
public class ValidationUtils
{
    /**
     * 验证空
     *
     * @param optional
     */
    public static void isNull(Optional<?> optional, String entity, String parameter, Object value)
    {
        if (!optional.isPresent())
        {
            String msg = entity + " 不存在 " + "{ " + parameter + CommonConstant.COLON_DELIMITER + value.toString() + " }";
            throw BadRequestException.newExceptionByCommon(msg);
        }
    }
}
