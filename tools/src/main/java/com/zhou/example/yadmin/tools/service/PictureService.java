/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.service.PictureService
 * </p>
 */
package com.zhou.example.yadmin.tools.service;

import java.util.Map;

import com.zhou.example.yadmin.tools.domain.Picture;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:40
 */
public interface PictureService
{
    /**
     * 上传图片
     *
     * @param file
     * @param username
     *
     * @return
     */
    Picture upload(MultipartFile file, String username);

    /**
     * 根据ID查询
     *
     * @param id
     *
     * @return
     */
    Picture findById(Long id);

    /**
     * 删除图片
     *
     * @param picture
     */
    void delete(Picture picture);

    /**
     * 分页
     *
     * @param picture
     * @param pageable
     *
     * @return
     */
    Map<String, Object> queryAll(Picture picture, Pageable pageable);
}
