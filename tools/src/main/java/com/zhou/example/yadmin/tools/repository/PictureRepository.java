/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.repository.PictureRepository
 * </p>
 */
package com.zhou.example.yadmin.tools.repository;

import com.zhou.example.yadmin.tools.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:31
 */
@Repository
public interface PictureRepository extends JpaRepository<Picture, Long>, JpaSpecificationExecutor<Picture>
{
}
