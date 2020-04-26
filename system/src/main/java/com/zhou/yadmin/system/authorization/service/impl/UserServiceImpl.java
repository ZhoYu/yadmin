/**
 * <p>
 * 文件名称:    UserServiceImpl
 * </p>
 */
package com.zhou.yadmin.system.authorization.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import cn.hutool.core.lang.Validator;
import com.google.common.collect.Lists;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.exception.EntityExistException;
import com.zhou.yadmin.common.exception.EntityNotFoundException;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.system.authorization.domain.User;
import com.zhou.yadmin.system.authorization.dto.UserDto;
import com.zhou.yadmin.system.authorization.mapper.UserMapper;
import com.zhou.yadmin.system.authorization.repository.UserRepository;
import com.zhou.yadmin.system.authorization.service.UserService;
import com.zhou.yadmin.system.core.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:37
 */
@Service
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

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
    public UserDto findById(long id)
    {
        Optional<User> user = userRepository.findById(id);
        ValidationUtils.isNull(user, "User", "id", id);
        return userMapper.toDto(user.get());
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
    public UserDto create(User resources)
    {
        if (userRepository.findByUsername(resources.getUsername()) != null)
        {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (userRepository.findByEmail(resources.getEmail()) != null)
        {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        if (resources.getRoles() == null || resources.getRoles().size() == 0)
        {
            throw BadRequestException.newExceptionBySystem("角色不能为空");
        }

        // 默认密码 123456
        resources.setPassword("14e1b600b1fd579f47433b88e8d85291");
        resources.setAvatar("https://i.loli.net/2018/12/06/5c08894d8de21.jpg");
        return userMapper.toDto(userRepository.save(resources));
    }

    /**
     * update
     *
     * @param resources
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources)
    {
        Optional<User> userOptional = userRepository.findById(resources.getId());
        ValidationUtils.isNull(userOptional, "User", "id", resources.getId());
        User user = userOptional.get();
        // 根据实际需求修改
        if (Objects.equals(user.getId(), 1L))
        {
            throw BadRequestException.newExceptionBySystem("该账号不能被修改");
        }
        User userByDb = userRepository.findByUsername(user.getUsername());

        if (resources.getRoles() == null || resources.getRoles().size() == 0)
        {
            throw BadRequestException.newExceptionBySystem("角色不能为空");
        }

        if (userByDb != null && !user.getId().equals(userByDb.getId()))
        {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        userByDb = userRepository.findByEmail(user.getEmail());
        if (userByDb != null && !user.getId().equals(userByDb.getId()))
        {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }

        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());

        userRepository.save(user);
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
        // 根据实际需求修改
        if (Objects.equals(id, 1L))
        {
            throw BadRequestException.newExceptionBySystem("该账号不能被删除");
        }
        userRepository.deleteById(id);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(JwtUser jwtUser, String pass)
    {
        userRepository.updatePass(jwtUser.getId(), pass);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(JwtUser jwtUser, String url)
    {
        userRepository.updateAvatar(jwtUser.getId(), url);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(JwtUser jwtUser, String email)
    {
        userRepository.updateEmail(jwtUser.getId(), email);
    }

    /**
     * findByName
     *
     * @param userName
     *
     * @return
     */
    @Override
    @Cacheable(key = "'findByName::'+#p0")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public UserDto findByName(String userName)
    {
        User user;
        if (Validator.isEmail(userName))
        {
            user = userRepository.findByEmail(userName);
        }
        else
        {
            user = userRepository.findByUsername(userName);
        }

        if (user == null)
        {
            throw new EntityNotFoundException(User.class, "name", userName);
        }
        else
        {
            return userMapper.toDto(user);
        }
    }

    /**
     * 查找用户 分页
     *
     * @param user
     * @param pageable
     *
     * @return
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public Map<String, Object> queryAll(UserDto user, Pageable pageable)
    {
        Page<User> page = userRepository.findAll(new CustomUserSpecification(user), pageable);
        return PageUtils.toPage(page.map(userMapper::toDto));
    }

    /**
     * 查找用户 不分页
     *
     * @param user
     *
     * @return
     */
    @Override
    @Cacheable(keyGenerator = "keyGenerator")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
    public List<UserDto> queryAll(UserDto user)
    {
        return userMapper.toDto(userRepository.findAll(new CustomUserSpecification(user)));
    }

    static class CustomUserSpecification implements Specification<User>
    {
        private static final long serialVersionUID = -6841782422302153633L;

        private UserDto user;

        public CustomUserSpecification(UserDto user)
        {
            this.user = user;
        }

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb)
        {
            List<Predicate> list = Lists.newArrayListWithCapacity(4);

            if (!ObjectUtils.isEmpty(user.getId()))
            {
                list.add(cb.equal(root.get("id").as(Long.class), user.getId()));
            }
            if (!ObjectUtils.isEmpty(user.getEnabled()))
            {
                list.add(cb.equal(root.get("enabled").as(Boolean.class), user.getEnabled()));
            }
            if (!ObjectUtils.isEmpty(user.getUsername()))
            {
                // 模糊查找
                list.add(cb.like(root.get("username").as(String.class),
                  CommonConstant.PERCENT_SIGN_DELIMITER + user.getUsername() + CommonConstant.PERCENT_SIGN_DELIMITER));
            }
            if (!ObjectUtils.isEmpty(user.getEmail()))
            {
                // 模糊查找
                list.add(cb.like(root.get("email").as(String.class),
                  CommonConstant.PERCENT_SIGN_DELIMITER + user.getEmail() + CommonConstant.PERCENT_SIGN_DELIMITER));
            }

            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
