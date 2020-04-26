/**
 * <p>
 * 文件名称:    PermissionService
 * </p>
 */
package com.zhou.yadmin.system.authorization.service;

import java.util.List;
import java.util.Map;

import com.zhou.yadmin.system.authorization.domain.Permission;
import com.zhou.yadmin.system.authorization.dto.PermissionDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:29
 */
public interface PermissionService
{
    /**
     * get
     * @param id
     * @return
     */
    PermissionDto findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    PermissionDto create(Permission resources);

    /**
     * update
     * @param resources
     */
    void update(Permission resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    List<Map<String, Object>> getPermissionTree(List<Permission> permissions);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<Permission> findByPid(long pid);

    /**
     * build Tree
     * @param permissionDtoList
     * @return
     */
    Map<String, Object> buildTree(List<PermissionDto> permissionDtoList);

    /**
     * 按名称模糊查找 不分页
     * @param name
     * @return
     */
    List<PermissionDto> queryAll(String name);
}
