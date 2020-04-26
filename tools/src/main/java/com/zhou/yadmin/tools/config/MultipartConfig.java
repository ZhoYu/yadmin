/**
 * <p>
 * 文件名称:    MultipartConfig
 * </p>
 */
package com.zhou.yadmin.tools.config;

import javax.servlet.MultipartConfigElement;
import java.io.File;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:15
 */
@Configuration
public class MultipartConfig
{
    private static final String TEMP_LOCATION = System.getProperty("user.dir") + "/data/tmp";

    /**
     * 文件上传临时路径
     */
    @Bean
    public MultipartConfigElement multipartConfigElement()
    {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        File tmpFile = new File(TEMP_LOCATION);
        if (!tmpFile.exists())
        {
            tmpFile.mkdirs();
        }
        factory.setLocation(TEMP_LOCATION);
        return factory.createMultipartConfig();
    }
}
