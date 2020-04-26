/**
 * <p>
 * 文件名称:    com.zhou.yadmin.tools.repository.QiNiuContentRepository
 * </p>
 */
package com.zhou.yadmin.tools.repository;

import com.zhou.yadmin.tools.domain.QiniuContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:16
 */
@Repository
public interface QiniuContentRepository extends JpaRepository<QiniuContent, Long>, JpaSpecificationExecutor<QiniuContent>
{
    /**
     * 根据key查询
     *
     * @param key
     *
     * @return
     */
    QiniuContent findByKey(String key);
}
