/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.aop.annotation.Log
 * </p>
 */
package com.zhou.example.yadmin.logging.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 日志注解
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 22:37
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log
{
    String value() default "";
}
