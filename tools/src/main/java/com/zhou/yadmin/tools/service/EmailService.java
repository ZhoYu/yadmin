/**
 * <p>
 * 文件名称:    EmailService
 * </p>
 */
package com.zhou.yadmin.tools.service;

import com.zhou.yadmin.tools.domain.EmailConfig;
import com.zhou.yadmin.tools.dto.EmailDto;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:38
 */
public interface EmailService
{
    /**
     * 更新邮件配置
     *
     * @param emailConfig
     * @param old
     *
     * @return
     */
    EmailConfig update(EmailConfig emailConfig, EmailConfig old);

    /**
     * 查询配置
     *
     * @return
     */
    EmailConfig find();

    /**
     * 发送邮件
     *
     * @param emailVo
     * @param emailConfig
     *
     * @throws Exception
     */
    void send(EmailDto emailVo, EmailConfig emailConfig) throws Exception;
}
