/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.repository.EmailRepository
 * </p>
 */
package com.zhou.example.yadmin.tools.repository;

import com.zhou.example.yadmin.tools.domain.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:30
 */
@Repository
public interface EmailRepository extends JpaRepository<EmailConfig, Long>
{
}
