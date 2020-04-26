/**
 * <p>
 * 文件名称:    GeneratorUtils
 * </p>
 */
package com.zhou.yadmin.generator.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhou.yadmin.common.utils.FileUtils;
import com.zhou.yadmin.generator.domain.GeneratorConfig;
import com.zhou.yadmin.generator.dto.ColumnInfoDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 代码生成
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 20:34
 */
public class GeneratorUtils
{
    private static final String TIMESTAMP = "Timestamp";

    private static final String BIGDECIMAL = "BigDecimal";

    private static final String PK = "PRI";

    /**
     * 获取后端代码模板名称
     *
     * @return
     */
    public static List<String> getAdminTemplateNames()
    {
        return Lists.newArrayList("Entity", "Dto", "Mapper", "Repository", "Service", "ServiceImpl", "Controller");
    }

    /**
     * 获取前端代码模板名称
     *
     * @return
     */
    public static List<String> getFrontTemplateNames()
    {
        return Lists.newArrayList("api", "index", "header", "edit", "elementForm");
    }

    /**
     * 生成代码
     *
     * @param columnInfoList 表元数据
     * @param generatorConfig 生成代码的参数配置，如包路径，作者
     */
    public static void generatorCode(List<ColumnInfoDto> columnInfoList, GeneratorConfig generatorConfig, String tableName) throws IOException
    {
        Map<String, Object> map = Maps.newHashMap();
        map.put("package", generatorConfig.getPack());
        map.put("moduleName", generatorConfig.getModuleName());
        map.put("author", generatorConfig.getAuthor());
        map.put("date", LocalDate.now().toString());
        map.put("tableName", tableName);
        String className = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, tableName);
        map.put("className", className);
        map.put("changeClassName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, tableName));
        map.put("hasTimestamp", false);
        map.put("hasBigDecimal", false);
        map.put("hasQuery", false);

        List<Map<String, Object>> columnMapList = Lists.newArrayList();
        List<Map<String, Object>> queryColumnMapList = Lists.newArrayList();
        for (ColumnInfoDto column : columnInfoList)
        {
            Map<String, Object> listMap = Maps.newHashMap();
            listMap.put("columnComment", column.getColumnComment());
            listMap.put("columnKey", column.getColumnKey());

            String colType = ColumnUtils.cloToJava(column.getColumnType().toString());
            if (PK.equals(column.getColumnKey()))
            {
                map.put("pkColumnType", colType);
            }
            if (TIMESTAMP.equals(colType))
            {
                map.put("hasTimestamp", true);
            }
            if (BigDecimal.class.getSimpleName().equals(colType))
            {
                map.put("hasBigDecimal", true);
            }
            listMap.put("columnType", colType);
            listMap.put("columnName", column.getColumnName());
            listMap.put("isNullable", column.getIsNullable());
            listMap.put("columnShow", column.getColumnShow());
            listMap.put("changeColumnName", CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, column.getColumnName().toString()));
            listMap.put("capitalColumnName", CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, column.getColumnName().toString()));

            if (!StringUtils.isBlank(column.getColumnQuery()))
            {
                listMap.put("columnQuery", column.getColumnQuery());
                map.put("hasQuery", true);
                queryColumnMapList.add(listMap);
            }
            columnMapList.add(listMap);
        }
        map.put("columns", columnMapList);
        map.put("queryColumns", queryColumnMapList);
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));

        // 生成后端代码
        List<String> templates = getAdminTemplateNames();
        for (String templateName : templates)
        {
            Template template = engine.getTemplate("generator/admin/" + templateName + ".ftl");
            String filePath = getAdminFilePath(templateName, generatorConfig, className);

            File file = new File(filePath);

            // 如果非覆盖生成
            if (!generatorConfig.getCover())
            {
                if (FileUtil.exist(file))
                {
                    continue;
                }
            }
            // 生成代码
            genFile(file, template, map);
        }

        // 生成前端代码
        templates = getFrontTemplateNames();
        for (String templateName : templates)
        {
            Template template = engine.getTemplate("generator/front/" + templateName + ".ftl");
            String filePath = getFrontFilePath(templateName, generatorConfig, map.get("changeClassName").toString());

            File file = new File(filePath);

            // 如果非覆盖生成
            if (!generatorConfig.getCover())
            {
                if (FileUtil.exist(file))
                {
                    continue;
                }
            }
            // 生成代码
            genFile(file, template, map);
        }
    }

    /**
     * 定义后端文件路径以及名称
     */
    public static String getAdminFilePath(String templateName, GeneratorConfig generatorConfig, String className)
    {
        String ProjectPath = System.getProperty("user.dir") + File.separator + generatorConfig.getModuleName();
        String packagePath = ProjectPath + File.separator + "src" + File.separator + "main" + File.separator + "java" + File.separator;
        if (!ObjectUtils.isEmpty(generatorConfig.getPack()))
        {
            packagePath += generatorConfig.getPack().replace(".", File.separator) + File.separator;
        }

        if ("Entity".equals(templateName))
        {
            return packagePath + "domain" + File.separator + className + ".java";
        }

        if ("Controller".equals(templateName))
        {
            return packagePath + "web" + File.separator + "controller" + File.separator + className + "Controller.java";
        }

        if ("Service".equals(templateName))
        {
            return packagePath + "service" + File.separator + className + "Service.java";
        }

        if ("ServiceImpl".equals(templateName))
        {
            return packagePath + "service" + File.separator + "impl" + File.separator + className + "ServiceImpl.java";
        }

        if ("Dto".equals(templateName))
        {
            return packagePath + "service" + File.separator + "dto" + File.separator + className + "DTO.java";
        }

        if ("Mapper".equals(templateName))
        {
            return packagePath + "service" + File.separator + "mapper" + File.separator + className + "Mapper.java";
        }
        // TODO: 没有必要存在QueryService
        if ("QueryService".equals(templateName))
        {
            return packagePath + "service" + File.separator + "query" + File.separator + className + "QueryService.java";
        }

        if ("Repository".equals(templateName))
        {
            return packagePath + "repository" + File.separator + className + "Repository.java";
        }

        return null;
    }

    /**
     * 定义前端文件路径以及名称
     */
    public static String getFrontFilePath(String templateName, GeneratorConfig generatorConfig, String apiName)
    {
        String path = generatorConfig.getPath();

        if ("api".equals(templateName))
        {
            return generatorConfig.getApiPath() + File.separator + apiName + ".js";
        }

        if ("index".equals(templateName))
        {
            return path + File.separator + "index.vue";
        }

        if ("header".equals(templateName))
        {
            return path + File.separator + "module" + File.separator + "header.vue";
        }

        if ("edit".equals(templateName))
        {
            return path + File.separator + "module" + File.separator + "edit.vue";
        }

        if ("elementForm".equals(templateName))
        {
            return path + File.separator + "module" + File.separator + "form.vue";
        }
        return null;
    }

    public static void genFile(File file, Template template, Map<String, Object> map) throws IOException
    {
        // 生成目标文件
        FileUtils.touch(file);
        try (Writer writer = new FileWriter(file))
        {
            template.render(map, writer);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args)
    {
        // System.out.println(FileUtil.exist("E:\\1.5.txt"));
    }
}
