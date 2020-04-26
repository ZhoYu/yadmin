/**
 * <p>
 * 文件名称:    VerificationCodeServiceImpl
 * </p>
 */
package com.zhou.yadmin.tools.service.impl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.hutool.core.lang.Dict;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import com.google.common.collect.Lists;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.tools.domain.VerificationCode;
import com.zhou.yadmin.tools.dto.EmailDto;
import com.zhou.yadmin.tools.repository.VerificationCodeRepository;
import com.zhou.yadmin.tools.service.VerificationCodeService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/13 20:18
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService
{
    @Value("${code.expiration}")
    private int expiration;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    /**
     * 发送邮件验证码
     *
     * @param verificationCode
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public EmailDto sendEmail(VerificationCode verificationCode)
    {
        EmailDto emailDto;
        String content;
        VerificationCode dbVerificationCode = verificationCodeRepository
          .findByScenesAndTypeAndValueAndStatusIsTrue(verificationCode.getScenes(), verificationCode.getType(), verificationCode.getValue());
        // 如果不存在有效的验证码，就创建一个新的
        TemplateEngine engine = TemplateUtil.createEngine(new TemplateConfig("template", TemplateConfig.ResourceMode.CLASSPATH));
        Template template = engine.getTemplate("email/email.ftl");
        if (dbVerificationCode == null)
        {
            verificationCode.setCode(RandomStringUtils.randomNumeric(6));
            content = template.render(Dict.create().set("code", verificationCode.getCode()));
            emailDto = new EmailDto(Lists.newArrayList(verificationCode.getValue()), "yadmin后台管理系统", content);
            timedDestruction(verificationCodeRepository.save(verificationCode));
        }
        else // 存在就再次发送原来的验证码
        {
            content = template.render(Dict.create().set("code", verificationCode.getCode()));
            emailDto = new EmailDto(Lists.newArrayList(dbVerificationCode.getValue()), "yadmin后台管理系统", content);
        }
        return emailDto;
    }

    /**
     * 验证
     *
     * @param verificationCode
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void validated(VerificationCode verificationCode)
    {
        VerificationCode dbVerificationCode = verificationCodeRepository
          .findByScenesAndTypeAndValueAndStatusIsTrue(verificationCode.getScenes(), verificationCode.getType(), verificationCode.getValue());
        if (dbVerificationCode == null || !dbVerificationCode.getCode().equals(verificationCode.getCode()))
        {
            throw BadRequestException.newExceptionByTools("无效验证码");
        }
        else
        {
            dbVerificationCode.setStatus(false);
            verificationCodeRepository.save(dbVerificationCode);
        }
    }

    /**
     * 定时任务，指定分钟后改变验证码状态
     *
     * @param verifyCode
     */
    private void timedDestruction(VerificationCode verifyCode)
    {
        //以下示例为程序调用结束继续运行
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try
        {
            executorService.schedule(() -> {
                verifyCode.setStatus(false);
                verificationCodeRepository.save(verifyCode);
            }, expiration * 60 * 1000L, TimeUnit.MILLISECONDS);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
