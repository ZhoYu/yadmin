/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.generator.service.impl.GeneratorServiceImpl
 * </p>
 */
package com.zhou.example.yadmin.generator.service.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;
import com.zhou.example.yadmin.common.exception.BadRequestException;
import com.zhou.example.yadmin.common.utils.PageUtils;
import com.zhou.example.yadmin.generator.domain.GeneratorConfig;
import com.zhou.example.yadmin.generator.dto.ColumnInfoDto;
import com.zhou.example.yadmin.generator.dto.TableInfoDto;
import com.zhou.example.yadmin.generator.service.GeneratorService;
import com.zhou.example.yadmin.generator.utils.GeneratorUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/24 19:41
 */
@Service
public class GeneratorServiceImpl implements GeneratorService
{
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * 查询数据库元数据
     *
     * @param name
     * @param startEnd
     *
     * @return
     */
    @Override
    public Object getTables(String name, int[] startEnd)
    {
        StringBuilder sql = new StringBuilder(
          "select table_name tableName, create_time createTime from information_schema.tables where table_schema = (select database()) ");
        if (!ObjectUtils.isEmpty(name))
        {
            sql.append("and table_name like '%").append(name).append("%' ");
        }
        sql.append("order by table_name");
        Query query = entityManager.createNativeQuery(sql.toString());
        query.setFirstResult(startEnd[0]);
        query.setMaxResults(startEnd[1]);

        System.out.println(sql.toString());
        List<Object[]> result = query.getResultList();
        List<TableInfoDto> tableInfos = Lists.newArrayList();
        for (Object[] data : result)
        {
            tableInfos.add(new TableInfoDto(data[0], data[1]));
        }
        Query query1 = entityManager.createNativeQuery("SELECT COUNT(*) from information_schema.tables where table_schema = (select database())");
        Object totalElements = query1.getSingleResult();
        return PageUtils.toPage(tableInfos, totalElements);
    }

    /**
     * 得到数据表的元数据
     *
     * @param name
     *
     * @return
     */
    @Override
    public Object getColumns(String name)
    {
        StringBuilder sql =
          new StringBuilder("select column_name, is_nullable, data_type, column_comment, column_key from information_schema.columns where ");
        if (!ObjectUtils.isEmpty(name))
        {
            sql.append("table_name = '").append(name).append("' ");
        }
        sql.append("and table_schema = (select database()) order by ordinal_position");
        Query query = entityManager.createNativeQuery(sql.toString());
        List<Object[]> result = query.getResultList();
        List<ColumnInfoDto> columnInfos = Lists.newArrayList();
        for (Object[] data : result)
        {
            columnInfos.add(new ColumnInfoDto(data[0], data[1], data[2], data[3], data[4], null, "true"));
        }
        return PageUtils.toPage(columnInfos, columnInfos.size());
    }

    /**
     * 生成代码
     *
     * @param columnInfoList
     * @param generatorConfig
     * @param tableName
     */
    @Override
    public void generator(List<ColumnInfoDto> columnInfoList, GeneratorConfig generatorConfig, String tableName)
    {
        if (generatorConfig.isNew())
        {
            throw BadRequestException.newExceptionByGenerator("请先配置生成器");
        }
        try
        {
            GeneratorUtils.generatorCode(columnInfoList, generatorConfig, tableName);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
