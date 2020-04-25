/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.generator.service.GeneratorConfigService
 * </p>
 */
package com.zhou.example.yadmin.generator.service;

import com.zhou.example.yadmin.generator.domain.GeneratorConfig;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 19:33
 */
public interface GeneratorConfigService
{
    /**
     * find
     * @return
     */
    GeneratorConfig find();

    /**
     * update
     * @param generatorConfig
     */
    GeneratorConfig update(GeneratorConfig generatorConfig);
}
