/**
 * <p>
 * 文件名称:    JwtUserDetailsService
 * </p>
 */
package com.zhou.yadmin.system.core.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import cn.hutool.core.lang.Validator;
import com.zhou.yadmin.common.exception.EntityNotFoundException;
import com.zhou.yadmin.system.authorization.domain.Permission;
import com.zhou.yadmin.system.authorization.domain.Role;
import com.zhou.yadmin.system.authorization.domain.User;
import com.zhou.yadmin.system.authorization.repository.PermissionRepository;
import com.zhou.yadmin.system.authorization.repository.UserRepository;
import com.zhou.yadmin.system.core.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:09
 */
@Service
public class JwtUserDetailsService implements UserDetailsService
{
    private static final String RESULT_ROLE_PREFIX = "ROLE_";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     *
     * @return a fully populated user record (never <code>null</code>)
     *
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     * GrantedAuthority
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user;
        if (Validator.isEmail(username))
        {
            user = userRepository.findByEmail(username);
        }
        else
        {
            user = userRepository.findByUsername(username);
        }

        if (user == null)
        {
            throw new EntityNotFoundException(User.class, "name", username);
        }
        else
        {
            return create(user);
        }
    }

    private UserDetails create(User user)
    {
        return new JwtUser(user.getId(), user.getUsername(), user.getPassword(), user.getAvatar(), user.getEmail(),
          mapToGrantedAuthorities(user.getRoles(), permissionRepository), user.getEnabled(), user.getCreateTime(), user.getLastPasswordResetTime());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Role> roles, PermissionRepository permissionRepository)
    {
        Set<Permission> permissions = new HashSet<>();
        for (Role role : roles)
        {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(role);
            permissions.addAll(permissionRepository.findByRolesIn(roleSet));
        }

        return permissions.stream().map(permission -> new SimpleGrantedAuthority(RESULT_ROLE_PREFIX + permission.getName()))
          .collect(Collectors.toList());
    }
}
