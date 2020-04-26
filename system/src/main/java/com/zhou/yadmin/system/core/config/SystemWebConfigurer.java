/**
 * <p>
 * 文件名称:    com.zhou.yadmin.system.core.config.CorsConfig
 * </p>
 */
package com.zhou.yadmin.system.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * WebMvcConfigurer 跨域请求
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:54
 */
@Configuration
@EnableWebMvc
public class SystemWebConfigurer implements WebMvcConfigurer
{
    @Override
    public void addCorsMappings(CorsRegistry registry)
    {
        //设置允许跨域的路径
        registry.addMapping("/**")
          //是否允许证书 不再默认开启
          .allowCredentials(true)
          //设置允许跨域请求的域名
          .allowedOrigins("*").allowedHeaders("*")
          //设置允许的方法
          .allowedMethods("GET", "POST", "PUT", "DELETE");
                // //设置允许的方法
                // .allowedMethods("*")
                // //跨域允许时间
                // .maxAge(3600);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/**").addResourceLocations("classpath:/META-INF/resources/").setCachePeriod(0);
    }
}
