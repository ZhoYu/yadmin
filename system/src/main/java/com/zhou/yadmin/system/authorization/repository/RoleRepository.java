/**
 * <p>
 * 文件名称:    RoleRepository
 * </p>
 */
package com.zhou.yadmin.system.authorization.repository;

import com.zhou.yadmin.system.authorization.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:33
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role>
{
    /**
     * findByName
     *
     * @param name
     *
     * @return
     */
    Role findByName(String name);
}
