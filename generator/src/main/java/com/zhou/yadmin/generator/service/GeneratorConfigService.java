/**
 * <p>
 * 文件名称:    GeneratorConfigService
 * </p>
 */
package com.zhou.yadmin.generator.service;

import com.zhou.yadmin.generator.domain.GeneratorConfig;

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
