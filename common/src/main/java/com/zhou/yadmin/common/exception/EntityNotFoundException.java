/**
 * <p>
 * 文件名称:    EntityNotFoundException
 * </p>
 */
package com.zhou.yadmin.common.exception;

/**
 * <p>
 * 找不到对应实体异常
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:35
 */
public class EntityNotFoundException extends BaseException
{
    private static final long serialVersionUID = -622043363329444047L;

    public EntityNotFoundException(Class<?> clazz, Object... searchParamsMap)
    {
        super(BaseException.ERROR_CODE_MODULE_COMMON,
          EntityNotFoundException.generateMessage(clazz.getSimpleName(), toMap(String.class, String.class, searchParamsMap)));
    }
}
