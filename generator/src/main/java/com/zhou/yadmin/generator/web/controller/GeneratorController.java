/**
 * <p>
 * 文件名称:    GeneratorController
 * </p>
 */
package com.zhou.yadmin.generator.web.controller;

import java.util.List;

import cn.hutool.core.util.PageUtil;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.generator.dto.ColumnInfoDto;
import com.zhou.yadmin.generator.service.GeneratorConfigService;
import com.zhou.yadmin.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 20:01
 */
@RestController
@RequestMapping("api/generator")
public class GeneratorController
{
    @Autowired
    private GeneratorService generatorService;

    @Autowired
    private GeneratorConfigService generatorConfigService;

    @Value("${generator.enabled}")
    private Boolean generatorEnabled;

    /**
     * 查询数据库元数据
     *
     * @param name
     * @param page
     * @param size
     *
     * @return
     */
    @GetMapping(value = "tables")
    public ResponseEntity getTables(@RequestParam(defaultValue = "") String name, @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size)
    {
        int[] startEnd = PageUtil.transToStartEnd(page + 1, size);
        return new ResponseEntity(generatorService.getTables(name, startEnd), HttpStatus.OK);
    }

    /**
     * 查询表内元数据
     *
     * @param tableName
     *
     * @return
     */
    @GetMapping(value = "columns")
    public ResponseEntity getTables(@RequestParam String tableName)
    {
        return new ResponseEntity(generatorService.getColumns(tableName), HttpStatus.OK);
    }

    /**
     * 生成代码
     *
     * @param columnInfoDtoList
     *
     * @return
     */
    @PostMapping(value = "")
    public ResponseEntity generator(@RequestBody List<ColumnInfoDto> columnInfoDtoList, @RequestParam String tableName)
    {
        if (!generatorEnabled)
        {
            throw BadRequestException.newExceptionByGenerator("此环境不允许生成代码！");
        }
        generatorService.generator(columnInfoDtoList, generatorConfigService.find(), tableName);
        return new ResponseEntity(HttpStatus.OK);
    }
}
