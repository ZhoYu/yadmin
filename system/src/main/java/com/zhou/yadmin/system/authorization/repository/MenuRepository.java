/**
 * <p>
 * 文件名称:    MenuRepository
 * </p>
 */
package com.zhou.yadmin.system.authorization.repository;

import java.util.List;
import java.util.Set;

import com.zhou.yadmin.system.authorization.domain.Menu;
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
 * @since 2020/2/28 21:30
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu>
{
    /**
     * findByName
     *
     * @param name
     *
     * @return
     */
    Menu findByName(String name);

    /**
     * findByRoles
     *
     * @param roleSet
     *
     * @return
     */
    List<Menu> findByRolesInOrderBySort(Set<Role> roleSet);

    /**
     * findByPid
     *
     * @param pid
     *
     * @return
     */
    List<Menu> findByPid(long pid);
}
