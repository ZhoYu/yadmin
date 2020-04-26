/**
 * <p>
 * 文件名称:    QiniuUtils
 * </p>
 */
package com.zhou.yadmin.tools.utils;

import java.time.LocalDateTime;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.zhou.yadmin.common.constants.DateConstant;
import org.apache.commons.io.FilenameUtils;

/**
 * <p>
 * 七牛云存储工具类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:50
 */
public final class QiniuUtils
{
    public static final String HUAD = "华东";

    public static final String HUAB = "华北";

    public static final String HUAN = "华南";

    public static final String BEIM = "北美";

    /**
     * 得到机房的对应关系
     *
     * @param zone
     *
     * @return
     */
    public static Configuration getConfiguration(String zone)
    {
        if (HUAD.equals(zone))
        {
            return new Configuration(Region.huadong());
        }
        else if (HUAB.equals(zone))
        {
            return new Configuration(Region.huabei());
        }
        else if (HUAN.equals(zone))
        {
            return new Configuration(Region.huanan());
        }
        else if (BEIM.equals(zone))
        {
            return new Configuration(Region.beimei());
        }
        else
        {
            return new Configuration(Region.xinjiapo()); // 否则就是东南亚
        }
    }

    /**
     * 默认不指定key的情况下，以文件内容的hash值作为文件名
     *
     * @param file
     *
     * @return
     */
    public static String getKey(String file)
    {
        return FilenameUtils.getBaseName(file) + LocalDateTime.now().format(DateConstant.FORMATTER_DATE_TIME_THIN) +
               FilenameUtils.EXTENSION_SEPARATOR + FilenameUtils.getExtension(file);
    }
}
