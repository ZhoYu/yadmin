/**
 * <p>
 * 文件名称:    RoleServiceImpl
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
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.system.authorization.repository.plugins.CustomLikeNameSpecification;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.dto.RoleDto;
import com.zhou.yadmin.system.authorization.mapper.RoleMapper;
import com.zhou.yadmin.system.authorization.repository.RoleRepository;
import com.zhou.yadmin.system.authorization.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 22:31
 */
@Service
@CacheConfig(cacheNames = "role")
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

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
    public RoleDto findById(long id)
    {
        Optional<Role> role = roleRepository.findById(id);
        ValidationUtils.isNull(role, "Role", "id", id);
        return roleMapper.toDto(role.get());
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
    public RoleDto create(Role resources)
    {
        if (roleRepository.findByName(resources.getName()) != null)
        {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }
        return roleMapper.toDto(roleRepository.save(resources));
    }

    /**
     * update
     *
     * @param resources
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Role resources)
    {
        Optional<Role> optionalRole = roleRepository.findById(resources.getId());
        ValidationUtils.isNull(optionalRole, "Role", "id", resources.getId());

        Role role = optionalRole.get();
        if (Objects.equals(1L, role.getId()))
        {
            throw BadRequestException.newExceptionBySystem("该角色不能被修改");
        }
        Role role1 = roleRepository.findByName(resources.getName());

        if (role1 != null && !Objects.equals(role1.getId(), role.getId()))
        {
            throw new EntityExistException(Role.class, "username", resources.getName());
        }

        role.setName(resources.getName());
        role.setRemark(resources.getRemark());
        role.setPermissions(resources.getPermissions());
        roleRepository.save(role);
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
            throw BadRequestException.newExceptionBySystem("该角色不能被删除");
        }
        roleRepository.deleteById(id);
    }

    /**
     * role tree
     *
     * @return
     */
    @Override
    @Cacheable(key = "'tree'")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<Map<String, Object>> getRoleTree()
    {
        List<Role> roleList = roleRepository.findAll();
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Role role : roleList)
        {
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", role.getId());
            map.put("label", role.getName());
            list.add(map);
        }
        return list;
    }

    /**
     * 根据 role 查询数据 分页
     * @param role
     * @param pageable
     * @return
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public Map<String, Object> queryAll(RoleDto role, Pageable pageable)
    {
        Page<Role> page = roleRepository.findAll(new CustomLikeNameSpecification<>(role.getName()), pageable);
        return PageUtils.toPage(page.map(roleMapper::toDto));
    }

    /**
     * 根据 role 查询数据 不分页
     * @param role
     * @return
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<RoleDto> queryAll(RoleDto role)
    {
        return roleMapper.toDto(roleRepository.findAll(new CustomLikeNameSpecification<>(role.getName())));
    }
}
