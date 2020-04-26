/**
 * <p>
 * 文件名称:    RoleService
 * </p>
 */
package com.zhou.yadmin.system.authorization.service;

import java.util.List;
import java.util.Map;

import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.RoleDto;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:30
 */
public interface RoleService
{
    /**
     * get
     *
     * @param id
     *
     * @return
     */
    RoleDto findById(long id);

    /**
     * create
     *
     * @param resources
     *
     * @return
     */
    RoleDto create(Role resources);

    /**
     * update
     *
     * @param resources
     */
    void update(Role resources);

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

    /**
     * role tree
     *
     * @return
     */
    List<Map<String, Object>> getRoleTree();

    /**
     * 根据 role 查询数据 分页
     * @param role
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(RoleDto role, Pageable pageable);

    /**
     * 根据 role 查询数据 不分页
     * @param role
     * @return
     */
    List<RoleDto> queryAll(RoleDto role);
}
