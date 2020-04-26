/**
 * <p>
 * 文件名称:    TestController
 * </p>
 */
package com.zhou.yadmin.system.monitor.web.controller;

import java.util.concurrent.atomic.AtomicInteger;

import com.zhou.yadmin.common.aop.annotation.Limit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/18 20:30
 */
@Controller
@RequestMapping("api")
public class LimitController
{
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();

    /**
     * 测试限流注解，下面配置说明该接口 60秒内最多只能访问 10次，保存到redis的键名为 limit_test，
     */
    @GetMapping("limit")
    @Limit(key = "test", period = 60, count = 10, name = "testLimit", prefix = "limit")
    public int testLimit()
    {
        return ATOMIC_INTEGER.incrementAndGet();
    }
}
