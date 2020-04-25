/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.repository.VerificationCodeRepository
 * </p>
 */
package com.zhou.example.yadmin.tools.repository;

import com.zhou.example.yadmin.tools.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/12 22:10
 */
@Repository
public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Long>
{
    /**
     * 获取有效的验证码
     *
     * @param scenes 业务场景，如重置密码，重置邮箱等等
     * @param type
     * @param value
     *
     * @return
     */
    VerificationCode findByScenesAndTypeAndValueAndStatusIsTrue(String scenes, String type, String value);
}
