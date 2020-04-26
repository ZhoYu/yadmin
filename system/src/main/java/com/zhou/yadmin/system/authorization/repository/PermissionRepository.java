/**
 * <p>
 * 文件名称:    PermissionRepository
 * </p>
 */
package com.zhou.yadmin.system.authorization.repository;

import java.util.List;
import java.util.Set;

import com.zhou.yadmin.system.authorization.domain.Permission;
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
 * @since 2020/2/28 21:32
 */
@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>, JpaSpecificationExecutor<Permission>
{
    /**
     * findByName
     *
     * @param name
     *
     * @return
     */
    Permission findByName(String name);

    /**
     * findByRoles
     *
     * @param roleSet
     *
     * @return
     */
    Set<Permission> findByRolesIn(Set<Role> roleSet);

    /**
     * findByPid
     *
     * @param pid
     *
     * @return
     */
    List<Permission> findByPid(long pid);
}
