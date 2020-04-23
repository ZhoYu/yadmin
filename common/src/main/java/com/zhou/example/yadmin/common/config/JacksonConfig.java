/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.config.JacksonConfig
 * </p>
 */
package com.zhou.example.yadmin.common.config;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.common.collect.Maps;
import com.zhou.example.yadmin.common.constants.DateConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/1 19:26
 */
@Configuration
public class JacksonConfig implements WebMvcConfigurer
{
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 设置自定义过的 objectMapper 不是用默认的 objectMapper
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        converters.forEach(converter -> {
            if (converter instanceof MappingJackson2HttpMessageConverter)
            {
                ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
            }
        });
    }

    /**
     * 自定义 jackson objectMapper 中的某些配置
     * @return
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer()
    {
        return builder -> {
            // NULL不参与序列化
            builder.serializationInclusion(JsonInclude.Include.NON_NULL);
            getDefaultFeatureMap().forEach((feature, enable) -> {
                if (enable)
                {
                    builder.featuresToEnable(feature);
                }
                else
                {
                    builder.featuresToDisable(feature);
                }
            });
            // 日期格式
            builder.simpleDateFormat(DateConstant.DATE_TIME_PATTERN);
            // java8日期日期处理
            builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(DateConstant.DATE_PATTERN)),
              new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateConstant.TIME_PATTERN)),
              new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_PATTERN)));
            builder.deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_PATTERN)),
              new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateConstant.TIME_PATTERN)),
              new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateConstant.DATE_PATTERN)));
        };
    }

    private Map<Object, Boolean> getDefaultFeatureMap()
    {
        Map<Object, Boolean> defaultFeatureMap = Maps.newHashMap();
        // 忽略json字符串中不识别的属性
        defaultFeatureMap.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 忽略无法转换的对象
        defaultFeatureMap.put(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // PrettyPrinter 格式化输出
        defaultFeatureMap.put(SerializationFeature.INDENT_OUTPUT, true);
        return defaultFeatureMap;
    }
}
