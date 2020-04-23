/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.exception.BaseException
 * </p>
 */
package com.zhou.example.yadmin.common.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 统一异常基类
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:24
 */
public class BaseException extends RuntimeException
{
    private static final long serialVersionUID = -1533970825666343411L;

    public static final int ERROR_CODE_MODULE_SYSTEM = 900009;
    public static final int ERROR_CODE_MODULE_LOGGING = 910009;
    public static final int ERROR_CODE_MODULE_TOOLS = 920009;
    public static final int ERROR_CODE_MODULE_COMMON = 990009;

    /**
     * 错误代码 - 统一约定的异常代码.
     */
    private int errorCode;

    public BaseException(int errorCode, String message)
    {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(int errorCode, String message, Throwable cause)
    {
        super(message, cause);
        this.errorCode = errorCode;
    }

    protected static String generateMessage(String entity, Map<String, String> searchParams)
    {
        return StringUtils.capitalize(entity) + " 不存在 " + searchParams;
    }

    protected static <K, V> Map<K, V> toMap(Class<K> keyType, Class<V> valueType, Object... entries)
    {
        if (entries.length % 2 == 1)
        {
            throw new IllegalArgumentException("Invalid entries");
        }
        return IntStream.range(0, entries.length / 2).map(i -> i * 2)
                        .collect(HashMap::new, (m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
    }

    /**
     * Gets the value of errorCode
     *
     * @return the value of errorCode
     */
    public int getErrorCode()
    {
        return errorCode;
    }

    /**
     * Sets the errorCode
     * <p>You can use getErrorCode() to get the value of errorCode</p>
     *
     * @param errorCode errorCode
     */
    public void setErrorCode(int errorCode)
    {
        this.errorCode = errorCode;
    }
}
