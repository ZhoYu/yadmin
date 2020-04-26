/**
 * <p>
 * 文件名称:    UserService
 * </p>
 */
package com.zhou.yadmin.system.authorization.service;

import java.util.List;
import java.util.Map;

import com.zhou.yadmin.system.core.security.JwtUser;
import com.zhou.yadmin.system.authorization.domain.User;
import com.zhou.yadmin.system.authorization.dto.UserDto;
import org.springframework.data.domain.Pageable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 23:32
 */
public interface UserService
{
    /**
     * get
     *
     * @param id
     *
     * @return
     */
    UserDto findById(long id);

    /**
     * create
     *
     * @param resources
     *
     * @return
     */
    UserDto create(User resources);

    /**
     * update
     *
     * @param resources
     */
    void update(User resources);

    /**
     * delete
     *
     * @param id
     */
    void delete(Long id);

    /**
     * 修改密码
     *
     * @param jwtUser
     * @param encryptPassword
     */
    void updatePass(JwtUser jwtUser, String encryptPassword);

    /**
     * 修改头像
     *
     * @param jwtUser
     * @param url
     */
    void updateAvatar(JwtUser jwtUser, String url);

    /**
     * 修改邮箱
     *
     * @param jwtUser
     * @param email
     */
    void updateEmail(JwtUser jwtUser, String email);

    /**
     * findByName
     *
     * @param userName
     *
     * @return
     */
    UserDto findByName(String userName);

    /**
     * 查找用户 分页
     * @param user
     * @param pageable
     * @return
     */
    Map<String, Object> queryAll(UserDto user, Pageable pageable);

    /**
     * 查找用户 不分页
     * @param user
     * @return
     */
    List<UserDto> queryAll(UserDto user);
}
