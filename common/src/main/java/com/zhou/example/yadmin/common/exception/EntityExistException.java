/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.exception.EntityExistException
 * </p>
 */
package com.zhou.example.yadmin.common.exception;

/**
 * <p>
 * 实体不存在异常
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:32
 */
public class EntityExistException extends BaseException
{
    private static final long serialVersionUID = 8267220412129650526L;

    public EntityExistException(Class<?> clazz, Object... saveBodyParamsMap)
    {
        super(BaseException.ERROR_CODE_MODULE_COMMON, generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, saveBodyParamsMap)));
    }
}
