/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.service.impl.EmailServiceImpl
 * </p>
 */
package com.zhou.example.yadmin.tools.service.impl;

import java.util.Optional;

import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.zhou.example.yadmin.common.exception.BadRequestException;
import com.zhou.example.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.example.yadmin.common.utils.EncryptUtils;
import com.zhou.example.yadmin.tools.domain.EmailConfig;
import com.zhou.example.yadmin.tools.dto.EmailDto;
import com.zhou.example.yadmin.tools.repository.EmailRepository;
import com.zhou.example.yadmin.tools.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:43
 */
@Service
@CacheConfig(cacheNames = "email")
public class EmailServiceImpl extends AbstractBaseComponent implements EmailService
{
    @Autowired
    private EmailRepository emailRepository;

    /**
     * 更新邮件配置
     *
     * @param emailConfig
     * @param old
     *
     * @return
     */
    @Override
    @CachePut(key = "'1'")
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public EmailConfig update(EmailConfig emailConfig, EmailConfig old)
    {
        try
        {
            if (!emailConfig.getPass().equals(old.getPass()))
            {
                emailConfig.setPass(EncryptUtils.desEncrypt(emailConfig.getPass())); // 对称加密
            }
        }
        catch (Exception e)
        {
            logger.error("加密失败", e);
        }
        emailRepository.saveAndFlush(emailConfig);
        return emailConfig;
    }

    /**
     * 查询配置
     *
     * @return
     */
    @Override
    @Cacheable(key = "'1'")
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    public EmailConfig find()
    {
        Optional<EmailConfig> emailConfig = emailRepository.findById(1L);
        return emailConfig.orElseGet(EmailConfig::new);
    }

    /**
     * 发送邮件
     *
     * @param emailDto
     * @param emailConfig
     *
     * @throws Exception
     */
    @Async
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void send(EmailDto emailDto, EmailConfig emailConfig) throws Exception
    {
        if (emailConfig == null)
        {
            throw BadRequestException.newExceptionByTools("请先配置，再操作");
        }
        // 封装
        MailAccount account = new MailAccount();
        account.setHost(emailConfig.getHost());
        account.setPort(Integer.parseInt(emailConfig.getPort()));
        account.setAuth(true);
        try
        {
            account.setPass(EncryptUtils.desDecrypt(emailConfig.getPass())); // 对称解密
        }
        catch (Exception e)
        {
            throw BadRequestException.newExceptionByTools(e.getMessage(), e);
        }
        account.setFrom(emailConfig.getUser() + "<" + emailConfig.getFromUser() + ">");
        // ssl方式发送
        account.setStarttlsEnable(true);
        String content = emailDto.getContent();

        try
        {
            // 发送
            MailUtil.send(account, emailDto.getToList(), emailDto.getSubject(), content, true);
        }
        catch (Exception e)
        {
            throw BadRequestException.newExceptionByTools(e.getMessage(), e);
        }
    }
}
