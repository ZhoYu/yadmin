/**
 * <p>
 * 文件名称:    GlobalExceptionHandler
 * </p>
 */
package com.zhou.yadmin.common.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import com.zhou.yadmin.common.dto.ErrorInfo;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.exception.EntityExistException;
import com.zhou.yadmin.common.exception.EntityNotFoundException;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * rest 全局控制层异常切面
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 22:25
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends AbstractBaseComponent
{
    /**
     * 处理所有不可知的异常
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e)
    {
        logger.error(e.getMessage(), e);
        ErrorInfo apiError = new ErrorInfo(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity handleAccessDeniedException(AccessDeniedException e)
    {
        logger.error(e.getMessage(), e);
        ErrorInfo apiError = new ErrorInfo(HttpStatus.FORBIDDEN.value(), e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * 处理自定义异常
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequestException(BadRequestException e)
    {
        logger.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo(BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(errorInfo);
    }

    /**
     * 处理 EntityExist
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler(value = EntityExistException.class)
    public ResponseEntity<ErrorInfo> entityExistException(EntityExistException e)
    {
        logger.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo(BAD_REQUEST.value(), e.getMessage());
        return buildResponseEntity(errorInfo);
    }

    /**
     * 处理 EntityNotFound
     *
     * @param e
     *
     * @return
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorInfo> entityNotFoundException(EntityNotFoundException e)
    {
        logger.error(e.getMessage(), e);
        ErrorInfo errorInfo = new ErrorInfo(NOT_FOUND.value(), e.getMessage());
        return buildResponseEntity(errorInfo);
    }

    /**
     * 处理所有接口数据验证异常
     *
     * @param e
     *
     * @returns
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        logger.error(e.getMessage(), e);
        String[] str = e.getBindingResult().getAllErrors().get(0).getCodes()[1].split("\\.");
        ErrorInfo errorInfo = new ErrorInfo(BAD_REQUEST.value(), str[1] + ":" + e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return buildResponseEntity(errorInfo);
    }

    /**
     * 统一返回
     *
     * @param errorInfo
     *
     * @return
     */
    private ResponseEntity<ErrorInfo> buildResponseEntity(ErrorInfo errorInfo)
    {
        return new ResponseEntity(errorInfo, HttpStatus.valueOf(errorInfo.getStatus()));
    }
}
