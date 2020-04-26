/**
 * <p>
 * 文件名称:    GeneratorService
 * </p>
 */
package com.zhou.yadmin.generator.service;

import java.util.List;

import com.zhou.yadmin.generator.domain.GeneratorConfig;
import com.zhou.yadmin.generator.dto.ColumnInfoDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 19:31
 */
public interface GeneratorService
{
    /**
     * 查询数据库元数据
     *
     * @param name
     * @param startEnd
     *
     * @return
     */
    Object getTables(String name, int[] startEnd);

    /**
     * 得到数据表的元数据
     *
     * @param name
     *
     * @return
     */
    Object getColumns(String name);

    /**
     * 生成代码
     *
     * @param columnInfoList
     * @param generatorConfig
     * @param tableName
     */
    void generator(List<ColumnInfoDto> columnInfoList, GeneratorConfig generatorConfig, String tableName);
}
