package com.zhou.yadmin.common.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

/**
 * <p>
 *  FreeMarkers工具类
 * </p>
 * <p>项目名称: springexample </p>
 * <p>作者: zhou   </p>
 * <p>日期: 2015-08-24 11:14   </p>
 * @author zhou
 */
public class FreeMarkerUtils
{
    private static final String TEMPLATE_PATH_GENERATOR = "/template/generator";
    private static final String TEMPLATE_PATH_EMAIL = "/template/email";
    private static Configuration configuration;
    private static FileOutputStream fileOut = null;

    static
    {
        buildConfiguration();
    }

    /**
     * Gets the value of configuration
     *
     * @return the value of configuration
     */
    public static Configuration getConfiguration()
    {
        return configuration;
    }

    public static String renderString(String templateString, Map<String, ?> model)
    {
        StringWriter writer = new StringWriter();
        try
        {
            Template template = new Template("name", new StringReader(templateString), new Configuration());
            template.process(model, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static String renderTemplate(Template template, Object model)
    {
        StringWriter writer = new StringWriter();
        try
        {
            template.process(model, writer);
            return writer.toString();
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static Configuration buildConfiguration(String directory) throws IOException
    {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        Resource resource = new DefaultResourceLoader().getResource(directory);
        configuration.setDirectoryForTemplateLoading(resource.getFile());
        return configuration;
    }

    private static Configuration buildConfiguration()
    {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_29);
        setTemplatePath(TEMPLATE_PATH_GENERATOR);
        setTemplatePath(TEMPLATE_PATH_EMAIL);
        return configuration;
    }

    private static Configuration setTemplatePath(String path)
    {
        configuration.setClassForTemplateLoading(FreeMarkerUtils.class, path);
        return configuration;
    }
}