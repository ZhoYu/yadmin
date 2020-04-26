/**
 * <p>
 * 文件名称:    GeneratorConfigServiceImpl
 * </p>
 */
package com.zhou.yadmin.generator.service.impl;

import java.util.Optional;

import com.zhou.yadmin.generator.domain.GeneratorConfig;
import com.zhou.yadmin.generator.repository.GeneratorConfigRepository;
import com.zhou.yadmin.generator.service.GeneratorConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 19:34
 */
@Service
@CacheConfig(cacheNames = "generatorConfig")
public class GeneratorConfigServiceImpl implements GeneratorConfigService
{
    @Autowired
    private GeneratorConfigRepository generatorConfigRepository;

    /**
     * find
     *
     * @return
     */
    @Override
    @Cacheable(key = "'1'")
    public GeneratorConfig find()
    {
        Optional<GeneratorConfig> genConfig = generatorConfigRepository.findById(1L);
        return genConfig.orElseGet(GeneratorConfig::new);
    }

    /**
     * update
     *
     * @param generatorConfig
     */
    @Override
    @CachePut(key = "'1'")
    public GeneratorConfig update(GeneratorConfig generatorConfig)
    {
        generatorConfig.setId(1L);
        return generatorConfigRepository.save(generatorConfig);
    }
}
