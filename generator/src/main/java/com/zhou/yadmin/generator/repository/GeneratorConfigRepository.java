/**
 * <p>
 * 文件名称:    GeneratorConfigRepository
 * </p>
 */
package com.zhou.yadmin.generator.repository;

import com.zhou.yadmin.generator.domain.GeneratorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 20:41
 */
@Repository
public interface GeneratorConfigRepository extends JpaRepository<GeneratorConfig, Long>
{
}
