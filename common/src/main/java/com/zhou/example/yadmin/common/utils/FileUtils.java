/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.common.utils.FileUtils
 * </p>
 */
package com.zhou.example.yadmin.common.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * File 工具类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/12 21:35
 */
public class FileUtils extends org.apache.commons.io.FileUtils
{
    /**
     * MultipartFile转File
     *
     * @param multipartFile
     *
     * @return
     */
    public static File toFile(MultipartFile multipartFile)
    {
        // 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        String extension = FilenameUtils.EXTENSION_SEPARATOR + FilenameUtils.getExtension(fileName);
        File file = null;
        try
        {
            // 用uuid作为文件名，防止生成的临时文件重复
            file = File.createTempFile(StringUtils.uuid(), extension);
            // MultipartFile to File
            multipartFile.transferTo(file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 创建File对象，自动识别相对或绝对路径，相对路径将自动从ClassPath下寻找
     *
     * @param path 文件路径
     * @return File
     */
    public static File file(String path) throws FileNotFoundException
    {
        if (null == path) {
            return null;
        }

        return ResourceUtils.getFile(path);
    }

    /**
     * 删除
     *
     * @param files
     */
    public static void deleteFile(File... files)
    {
        for (File file : files)
        {
            if (file.exists())
            {
                file.delete();
            }
        }
    }

    /**
     * 文件大小转换
     *
     * @param size
     *
     * @return
     */
    public static String getSize(int size)
    {
        return byteCountToDisplaySize(size);
    }
}
