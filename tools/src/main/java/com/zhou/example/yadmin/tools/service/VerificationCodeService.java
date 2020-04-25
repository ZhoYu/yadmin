/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.service.VerificationCodeService
 * </p>
 */
package com.zhou.example.yadmin.tools.service;

import com.zhou.example.yadmin.tools.domain.VerificationCode;
import com.zhou.example.yadmin.tools.dto.EmailDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/13 19:05
 */
public interface VerificationCodeService
{
    /**
     * 发送邮件验证码
     *
     * @param verificationCode
     */
    EmailDto sendEmail(VerificationCode verificationCode);

    /**
     * 验证
     *
     * @param verificationCode
     */
    void validated(VerificationCode verificationCode);
}
