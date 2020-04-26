/**
 * <p>
 * 文件名称:    com.zhou.yadmin.tools.service.QiNiuService
 * </p>
 */
package com.zhou.yadmin.tools.service;

import com.zhou.yadmin.tools.domain.QiniuConfig;
import com.zhou.yadmin.tools.domain.QiniuContent;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:45
 */
public interface QiniuService
{
    /**
     * 分页
     *
     * @param qiniuContent
     * @param pageable
     *
     * @return
     */
    Object queryAll(QiniuContent qiniuContent, Pageable pageable);

    /**
     * 查配置
     *
     * @return
     */
    QiniuConfig find();

    /**
     * 修改配置
     *
     * @param qiniuConfig
     *
     * @return
     */
    QiniuConfig update(QiniuConfig qiniuConfig);

    /**
     * 上传文件
     *
     * @param file
     * @param qiniuConfig
     */
    QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig);

    /**
     * 查询文件
     *
     * @param id
     *
     * @return
     */
    QiniuContent findByContentId(Long id);

    /**
     * 下载文件
     *
     * @param content
     * @param config
     *
     * @return
     */
    String download(QiniuContent content, QiniuConfig config);

    /**
     * 删除文件
     *
     * @param content
     * @param config
     *
     * @return
     */
    void delete(QiniuContent content, QiniuConfig config);

    /**
     * 同步数据
     *
     * @param config
     */
    void synchronize(QiniuConfig config);
}
