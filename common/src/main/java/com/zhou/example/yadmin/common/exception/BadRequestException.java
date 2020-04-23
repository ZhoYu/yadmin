/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.exception.BadRequestException
 * </p>
 */
package com.zhou.example.yadmin.common.exception;

import org.springframework.http.HttpStatus;

/**
 * <p>
 * 请求失败异常
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:30
 */
public class BadRequestException extends BaseException
{
    private static final long serialVersionUID = 5915252491073335045L;

    private Integer status = HttpStatus.BAD_REQUEST.value();

    public BadRequestException(int errorCode, String message)
    {
        super(errorCode, message);
    }

    public BadRequestException(int errorCode, String message, Throwable cause)
    {
        super(errorCode, message, cause);
    }

    public BadRequestException(int errorCode, HttpStatus status, String message)
    {
        super(errorCode, message);
        this.status = status.value();
    }

    public BadRequestException(int errorCode, HttpStatus status, String message, Throwable cause)
    {
        super(errorCode, message, cause);
        this.status = status.value();
    }

    public static BadRequestException newExceptionBySystem(String message)
    {
        return new BadRequestException(ERROR_CODE_MODULE_SYSTEM, message);
    }

    public static BadRequestException newExceptionBySystem(String message, Throwable cause)
    {
        return new BadRequestException(ERROR_CODE_MODULE_SYSTEM, message, cause);
    }

    public static BadRequestException newExceptionByLogging(String message)
    {
        return new BadRequestException(ERROR_CODE_MODULE_LOGGING, message);
    }

    public static BadRequestException newExceptionByLogging(String message, Throwable cause)
    {
        return new BadRequestException(ERROR_CODE_MODULE_LOGGING, message, cause);
    }

    public static BadRequestException newExceptionByTools(String message)
    {
        return new BadRequestException(ERROR_CODE_MODULE_TOOLS, message);
    }

    public static BadRequestException newExceptionByTools(String message, Throwable cause)
    {
        return new BadRequestException(ERROR_CODE_MODULE_TOOLS, message, cause);
    }

    public static BadRequestException newExceptionByCommon(String message)
    {
        return new BadRequestException(ERROR_CODE_MODULE_COMMON, message);
    }

    public static BadRequestException newExceptionByCommon(String message, Throwable cause)
    {
        return new BadRequestException(ERROR_CODE_MODULE_COMMON, message, cause);
    }

    /**
     * Gets the value of status
     *
     * @return the value of status
     */
    public Integer getStatus()
    {
        return status;
    }
}
