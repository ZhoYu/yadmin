/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.generator.repository.GeneratorConfigRepository
 * </p>
 */
package com.zhou.example.yadmin.generator.repository;

import com.zhou.example.yadmin.generator.domain.GeneratorConfig;
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
