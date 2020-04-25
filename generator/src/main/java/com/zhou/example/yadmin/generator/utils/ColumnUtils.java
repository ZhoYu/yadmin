/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.generator.utils.ColumnUtils
 * </p>
 */
package com.zhou.example.yadmin.generator.utils;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;

/**
 * <p>
 * sql字段转java
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 20:04
 */
public class ColumnUtils
{
    /**
     * 转换mysql数据类型为java数据类型
     * @param type
     * @return
     */
    public static String cloToJava(String type){
        Configuration config = getConfig();
        return config.getString(type,"unknowType");
    }

    /**
     * 获取配置信息
     */
    public static PropertiesConfiguration getConfig() {
        // try {
        //     return new PropertiesConfiguration("generator.properties" );
        // } catch (ConfigurationException e) {
        //     e.printStackTrace();
        // }
        return null;
    }
}
