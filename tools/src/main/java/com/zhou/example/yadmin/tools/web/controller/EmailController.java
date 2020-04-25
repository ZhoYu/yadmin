/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.web.controller.EmailController
 * </p>
 */
package com.zhou.example.yadmin.tools.web.controller;

import com.zhou.example.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.example.yadmin.logging.aop.annotation.Log;
import com.zhou.example.yadmin.tools.domain.EmailConfig;
import com.zhou.example.yadmin.tools.dto.EmailDto;
import com.zhou.example.yadmin.tools.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 发送邮件
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/16 2:12
 */
@RestController
@RequestMapping("api/email")
public class EmailController extends AbstractBaseComponent
{
    @Autowired
    private EmailService emailService;

    @GetMapping(value = "")
    public ResponseEntity get()
    {
        return new ResponseEntity(emailService.find(), HttpStatus.OK);
    }

    @Log("配置邮件")
    @PutMapping(value = "")
    public ResponseEntity emailConfig(@Validated @RequestBody EmailConfig emailConfig)
    {
        emailService.update(emailConfig, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("发送邮件")
    @PostMapping(value = "")
    public ResponseEntity send(@Validated @RequestBody EmailDto emailDto) throws Exception
    {
        logger.warn("REST request to send Email : {}", emailDto);
        emailService.send(emailDto, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
