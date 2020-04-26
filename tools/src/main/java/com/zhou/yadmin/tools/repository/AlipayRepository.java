/**
 * <p>
 * 文件名称:    AlipayRepository
 * </p>
 */
package com.zhou.yadmin.tools.repository;

import com.zhou.yadmin.tools.domain.AlipayConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 21:13
 */
@Repository
public interface AlipayRepository extends JpaRepository<AlipayConfig, Long>
{
}
