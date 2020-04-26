/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.SystemApplication
 * </p>
 */
package com.zhou.yadmin.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * <p>
 * yadmin 后台启动类
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/27 21:59
 */
@EnableScheduling // 开启定时任务
@SpringBootApplication
@EnableTransactionManagement
public class SystemApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(SystemApplication.class, args);
    }
}
