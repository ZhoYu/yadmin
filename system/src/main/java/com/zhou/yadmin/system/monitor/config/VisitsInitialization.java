package com.zhou.yadmin.system.monitor.config;

import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.system.monitor.service.VisitsService;
import org.springframework.context.annotation.Configuration;

/**
 * 初始化站点统计
 */
@Configuration
public class VisitsInitialization extends AbstractBaseComponent
{

    public VisitsInitialization(VisitsService visitsService)
    {
        logger.info("--------------- 初始化站点统计，如果存在今日统计则跳过 ---------------");
        visitsService.save();
        logger.info("--------------- 初始化站点统计完成 ---------------");
    }
}