/**
 * <p>
 * 文件名称:    MenuService
 * </p>
 */
package com.zhou.yadmin.system.authorization.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.zhou.yadmin.system.authorization.domain.Menu;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.MenuDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:36
 */
public interface MenuService
{
    /**
     * get
     * @param id
     * @return
     */
    MenuDto findById(long id);

    /**
     * create
     * @param resources
     * @return
     */
    MenuDto create(Menu resources);

    /**
     * update
     * @param resources
     */
    void update(Menu resources);

    /**
     * delete
     * @param id
     */
    void delete(Long id);

    /**
     * permission tree
     * @return
     */
    List<Map<String, Object>> getMenuTree(List<MenuDto> menus);

    /**
     * findByPid
     * @param pid
     * @return
     */
    List<MenuDto> findByPid(long pid);

    /**
     * build Tree
     * @param menuDtoList
     * @return
     */
    Map<String, Object> buildTree(List<MenuDto> menuDtoList);

    /**
     * findByRoles
     * @param roles
     * @return
     */
    List<MenuDto> findByRoles(Set<Role> roles);

    /**
     * buildMenus
     * @param menuDtoList
     * @return
     */
    List<MenuDto> buildMenus(List<MenuDto> menuDtoList);

    /**
     * 按名称查找所有数据 不分页
     * @param name
     * @return
     */
    List<MenuDto> queryAll(String name);
}
