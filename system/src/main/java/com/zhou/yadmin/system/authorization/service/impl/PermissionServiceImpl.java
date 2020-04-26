/**
 * <p>
 * 文件名称:    PermissionServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.authorization.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.exception.EntityExistException;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.system.authorization.repository.plugins.CustomLikeNameSpecification;
import com.zhou.yadmin.system.authorization.domain.Permission;
import com.zhou.yadmin.system.authorization.dto.PermissionDto;
import com.zhou.yadmin.system.authorization.mapper.PermissionMapper;
import com.zhou.yadmin.system.authorization.repository.PermissionRepository;
import com.zhou.yadmin.system.authorization.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 22:14
 */
@Service
@CacheConfig(cacheNames = "permission")
public class PermissionServiceImpl implements PermissionService
{
    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

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
    public PermissionDto findById(long id)
    {
        Optional<Permission> permission = permissionRepository.findById(id);
        ValidationUtils.isNull(permission, "Permission", "id", id);
        return permissionMapper.toDto(permission.get());
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
    @Transactional(rollbackFor = Exception.class)
    public PermissionDto create(Permission resources)
    {
        if (permissionRepository.findByName(resources.getName()) != null)
        {
            throw new EntityExistException(Permission.class, "name", resources.getName());
        }
        return permissionMapper.toDto(permissionRepository.save(resources));
    }

    /**
     * update
     *
     * @param resources
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Permission resources)
    {
        Optional<Permission> optionalPermission = permissionRepository.findById(resources.getId());
        ValidationUtils.isNull(optionalPermission, "Permission", "id", resources.getId());
        Permission permission = optionalPermission.get();

        /*
          根据实际需求修改
         */
        if (Objects.equals(permission.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该权限不能被修改");
        }

        Permission permission1 = permissionRepository.findByName(resources.getName());

        if (permission1 != null && !Objects.equals(permission1.getId(), permission.getId()))
        {
            throw new EntityExistException(Permission.class, "name", resources.getName());
        }

        permission.setName(resources.getName());
        permission.setAlias(resources.getAlias());
        permission.setPid(resources.getPid());
        permissionRepository.save(permission);
    }

    /**
     * delete
     *
     * @param id
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id)
    {
        /*
          根据实际需求修改
         */
        if (Objects.equals(id, 1L))
        {
            throw BadRequestException.newExceptionBySystem("该权限不能被删除");
        }
        List<Permission> permissionList = permissionRepository.findByPid(id);
        for (Permission permission : permissionList)
        {
            permissionRepository.delete(permission);
        }
        permissionRepository.deleteById(id);
    }

    /**
     * permission tree
     *
     * @param permissions
     *
     * @return
     */
    @Override
    @Cacheable(key = "'tree'")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<Map<String, Object>> getPermissionTree(List<Permission> permissions)
    {
        List<Map<String, Object>> list = Lists.newLinkedList();
        permissions.forEach(permission -> {
            if (permission != null)
            {
                List<Permission> permissionList = permissionRepository.findByPid(permission.getId());
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", permission.getId());
                map.put("label", permission.getAlias());
                if (permissionList != null && permissionList.size() != 0)
                {
                    map.put("children", getPermissionTree(permissionList));
                }
                list.add(map);
            }
        });
        return list;
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
    public List<Permission> findByPid(long pid)
    {
        return permissionRepository.findByPid(pid);
    }

    /**
     * build Tree
     *
     * @param permissionDtoList
     *
     * @return
     */
    @Override
    public Map<String, Object> buildTree(List<PermissionDto> permissionDtoList)
    {
        List<PermissionDto> trees = Lists.newArrayList();

        if (permissionDtoList != null)
        {
            for (PermissionDto permissionDTO : permissionDtoList)
            {
                if ("0".equals(permissionDTO.getPid().toString()))
                {
                    trees.add(permissionDTO);
                }

                for (PermissionDto it : permissionDtoList)
                {
                    if (it.getPid().equals(permissionDTO.getId()))
                    {
                        if (permissionDTO.getChildren() == null)
                        {
                            permissionDTO.setChildren(Lists.newArrayList());
                        }
                        permissionDTO.getChildren().add(it);
                    }
                }
            }
        }

        int totalElements = permissionDtoList != null ? permissionDtoList.size() : 0;
        Map<String, Object> map = Maps.newHashMap();
        map.put("content", trees.size() == 0 ? permissionDtoList : trees);
        map.put("totalElements", totalElements);
        return map;
    }

    /**
     * 按名称模糊查找 不分页
     * @param name
     * @return
     */
    @Override
    @Cacheable(key = "'queryAll:'+#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<PermissionDto> queryAll(String name)
    {
        return permissionMapper.toDto(permissionRepository.findAll(new CustomLikeNameSpecification<>(name)));
    }
}
