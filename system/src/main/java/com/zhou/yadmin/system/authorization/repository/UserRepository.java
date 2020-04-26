/**
 * <p>
 * 文件名称:    UserRepository
 * </p>
 */
package com.zhou.yadmin.system.authorization.repository;

import com.zhou.yadmin.system.authorization.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:34
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>
{
    /**
     * findByUsername
     *
     * @param username
     *
     * @return
     */
    @Query("from User u join fetch u.roles where u.username = ?1")
    User findByUsername(String username);

    /**
     * findByEmail
     *
     * @param email
     *
     * @return
     */
    @Query("from User u join fetch u.roles where u.email = ?1")
    User findByEmail(String email);

    /**
     * 修改密码
     *
     * @param id
     * @param pass
     */
    @Modifying
    @Query(value = "update t_user set password = ?2 where id = ?1", nativeQuery = true)
    void updatePass(Long id, String pass);

    /**
     * 修改头像
     *
     * @param id
     * @param url
     */
    @Modifying
    @Query(value = "update t_user set avatar = ?2 where id = ?1", nativeQuery = true)
    void updateAvatar(Long id, String url);

    /**
     * 修改邮箱
     *
     * @param id
     * @param email
     */
    @Modifying
    @Query(value = "update t_user set email = ?2 where id = ?1", nativeQuery = true)
    void updateEmail(Long id, String email);
}
