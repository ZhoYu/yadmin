/**
 * <p>
 * 文件名称:    Limit
 * </p>
 */
package com.zhou.yadmin.common.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.zhou.yadmin.common.enums.LimitType;

/**
 * <p>
 * 限流注解
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/18 19:36
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit
{
    /**
     * 资源名称, 用于描述接口功能
     *
     * @return
     */
    String name() default "";

    /**
     * 资源 key
     */
    String key() default "";

    /**
     * key 前缀
     */
    String prefix() default "request_limit";

    /**
     * 时间 秒
     */
    long period();

    /**
     * 限制访问次数
     */
    int count();

    /**
     * 限流类型
     *
     * @return
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
