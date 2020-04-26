/**
 * <p>
 * 文件名称:    MenuServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.authorization.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.constants.FrontConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.exception.EntityExistException;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.system.authorization.repository.plugins.CustomLikeNameSpecification;
import com.zhou.yadmin.system.authorization.domain.Menu;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.MenuDto;
import com.zhou.yadmin.system.authorization.dto.MenuMetaDto;
import com.zhou.yadmin.system.authorization.mapper.MenuMapper;
import com.zhou.yadmin.system.authorization.repository.MenuRepository;
import com.zhou.yadmin.system.authorization.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:56
 */
@Service
@CacheConfig(cacheNames = "menu")
public class MenuServiceImpl implements MenuService
{
    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuMapper menuMapper;

    /**
     * get
     *
     * @param id
     *
     * @return
     */
    @Override
    @Cacheable(key = "#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public MenuDto findById(long id)
    {
        Optional<Menu> menu = menuRepository.findById(id);
        ValidationUtils.isNull(menu, "Menu", "id", id);
        return menuMapper.toDto(menu.get());
    }

    /**
     * create
     *
     * @param resources
     *
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public MenuDto create(Menu resources)
    {
        if (menuRepository.findByName(resources.getName()) != null)
        {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }
        checkMenuUrlScheme(resources);
        return menuMapper.toDto(menuRepository.save(resources));
    }

    /**
     * update
     *
     * @param resources
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void update(Menu resources)
    {
        Optional<Menu> optionalPermission = menuRepository.findById(resources.getId());
        ValidationUtils.isNull(optionalPermission, "Permission", "id", resources.getId());
        checkMenuUrlScheme(resources);

        Menu menu = optionalPermission.get();
        Menu menuByDb = menuRepository.findByName(resources.getName());

        if (menuByDb != null && !menuByDb.getId().equals(menu.getId()))
        {
            throw new EntityExistException(Menu.class, "name", resources.getName());
        }

        menu.setName(resources.getName());
        menu.setComponent(resources.getComponent());
        menu.setPath(resources.getPath());
        menu.setIcon(resources.getIcon());
        menu.setiIframe(resources.getiIframe());
        menu.setPid(resources.getPid());
        menu.setSort(resources.getSort());
        menu.setRoles(resources.getRoles());
        menuRepository.save(menu);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void delete(Long id)
    {
        List<Menu> menuList = menuRepository.findByPid(id);
        for (Menu menu : menuList)
        {
            menuRepository.delete(menu);
        }
        menuRepository.deleteById(id);
    }

    /**
     * permission tree
     *
     * @param menus
     *
     * @return
     */
    @Override
    @Cacheable(key = "'tree'")
    public List<Map<String, Object>> getMenuTree(List<MenuDto> menus)
    {
        List<Map<String, Object>> tree = Lists.newLinkedList();
        if (!CollectionUtils.isEmpty(menus))
        {
            menus.forEach(menu -> {
                if (menu != null)
                {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("id", menu.getId());
                    map.put("label", menu.getName());
                    if (!CollectionUtils.isEmpty(menu.getChildren()))
                    {
                        map.put("children", getMenuTree(menu.getChildren()));
                    }
                    tree.add(map);
                }
            });
        }
        return tree;
    }

    /**
     * findByPid
     *
     * @param pid
     *
     * @return
     */
    @Override
    @Cacheable(key = "'pid:'+#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<MenuDto> findByPid(long pid)
    {
        List<MenuDto> menuDtoList = Lists.newArrayList();
        List<Menu> menuList = menuRepository.findByPid(pid);
        if (!CollectionUtils.isEmpty(menuList))
        {
            menuDtoList.addAll(menuMapper.toDto(menuList));
        }
        return menuDtoList;
    }

    /**
     * build Tree
     *
     * @param menuDtoList
     *
     * @return
     */
    @Override
    public Map<String, Object> buildTree(List<MenuDto> menuDtoList)
    {
        List<MenuDto> trees = Lists.newArrayList();

        if (menuDtoList != null)
        {
            for (MenuDto menuDTO : menuDtoList)
            {
                if ("0".equals(menuDTO.getPid().toString()))
                {
                    trees.add(menuDTO);
                }
                for (MenuDto it : menuDtoList)
                {
                    if (it.getPid().equals(menuDTO.getId()))
                    {
                        if (menuDTO.getChildren() == null)
                        {
                            menuDTO.setChildren(Lists.newArrayList());
                        }
                        menuDTO.getChildren().add(it);
                    }
                }
            }
        }

        int totalElements = menuDtoList != null ? menuDtoList.size() : 0;
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", trees.size() == 0 ? menuDtoList : trees);
        map.put("totalElements", totalElements);
        return map;
    }

    /**
     * findByRoles
     *
     * @param roles
     *
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<MenuDto> findByRoles(Set<Role> roles)
    {
        List<Menu> menus = menuRepository.findByRolesInOrderBySort(roles);
        return menus.stream().map(menuMapper::toDto).collect(Collectors.toList());
    }

    /**
     * buildMenus
     *
     * @param menuDtoList
     *
     * @return
     */
    @Override
    public List<MenuDto> buildMenus(List<MenuDto> menuDtoList)
    {
        List<MenuDto> list = new LinkedList<>();
        menuDtoList.forEach(menu -> {
            if (menu != null)
            {
                List<MenuDto> menuList = menu.getChildren();
                MenuDto menuDto = new MenuDto();
                menuDto.setName(menu.getName());
                menuDto.setPath(menu.getPath());
                if (!Boolean.TRUE.equals(menu.getIframe())) // 非外链
                {
                    if (menu.getPid().equals(0L))
                    {
                        //一级目录需要加斜杠，不然访问不了
                        menuDto.setPath(CommonConstant.SLASH_DELIMITER + menu.getPath());
                        menuDto
                          .setComponent(StringUtils.isEmpty(menu.getComponent()) ? FrontConstant.FRONT_COMPONENT_DEFAULT_NAME : menu.getComponent());
                    }
                    else if (StringUtils.isNotBlank(menu.getComponent()))
                    {
                        menuDto.setComponent(menu.getComponent());
                    }
                }
                menuDto.setMeta(new MenuMetaDto(menu.getName(), menu.getIcon()));
                if (menuList != null && menuList.size() != 0)
                {
                    menuDto.setAlwaysShow(true);
                    menuDto.setRedirect(FrontConstant.FRONT_REDIRECT_PATH_NONE);
                    menuDto.setChildren(buildMenus(menuList));
                }
                else if (menu.getPid().equals(0L))
                {
                    MenuDto childMenuDto = new MenuDto();
                    childMenuDto.setMeta(menuDto.getMeta());
                    if (Boolean.TRUE.equals(menu.getIframe())) // 外链
                    {
                        childMenuDto.setPath(menu.getPath());
                    }
                    else
                    {
                        childMenuDto.setPath(FrontConstant.FRONT_PATH_INDEX_DEFAULT_NAME);
                        childMenuDto.setName(menuDto.getName());
                        childMenuDto.setComponent(menuDto.getComponent());
                    }
                    menuDto.setName(null);
                    menuDto.setMeta(null);
                    menuDto.setComponent(FrontConstant.FRONT_COMPONENT_DEFAULT_NAME);
                    menuDto.setChildren(Lists.newArrayList(childMenuDto));
                }
                list.add(menuDto);
            }
        });
        return list;
    }

    /**
     * 不分页
     * @return
     */
    @Override
    @Cacheable(key = "'queryAll:'+#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<MenuDto> queryAll(String name)
    {
        return menuMapper.toDto(menuRepository.findAll(new CustomLikeNameSpecification<>(name)));
    }

    /**
     * 检查 菜单 外链 url
     * @param resources
     */
    private void checkMenuUrlScheme(Menu resources)
    {
        if (Boolean.TRUE.equals(resources.getiIframe()))
        {
            if (!resources.getPath().toLowerCase().startsWith(FrontConstant.URL_PROTOCOL_HTTP) &&
                !resources.getPath().toLowerCase().startsWith(FrontConstant.URL_PROTOCOL_HTTPS))
            {
                throw BadRequestException.newExceptionBySystem("外链必须以http://或者https://开头");
            }
        }
    }
}
