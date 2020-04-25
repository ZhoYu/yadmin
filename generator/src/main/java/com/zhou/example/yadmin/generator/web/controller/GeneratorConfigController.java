/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.generator.web.controller.GeneratorConfigController
 * </p>
 */
package com.zhou.example.yadmin.generator.web.controller;

import com.zhou.example.yadmin.generator.domain.GeneratorConfig;
import com.zhou.example.yadmin.generator.service.GeneratorConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 19:58
 */
@RestController
@RequestMapping("api/generatorConfig")
public class GeneratorConfigController
{
    @Autowired
    private GeneratorConfigService generatorConfigService;

    /**
     * 查询生成器配置
     *
     * @return
     */
    @GetMapping(value = "")
    public ResponseEntity get()
    {
        return new ResponseEntity(generatorConfigService.find(), HttpStatus.OK);
    }

    @PutMapping(value = "")
    public ResponseEntity emailConfig(@Validated @RequestBody GeneratorConfig generatorConfig)
    {
        return new ResponseEntity(generatorConfigService.update(generatorConfig), HttpStatus.OK);
    }
}
