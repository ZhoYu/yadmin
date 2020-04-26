/**
 * <p>
 * 文件名称:    VerificationCodeController
 * </p>
 */
package com.zhou.yadmin.tools.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.zhou.yadmin.common.constants.FrontConstant;
import com.zhou.yadmin.tools.domain.VerificationCode;
import com.zhou.yadmin.tools.dto.EmailDto;
import com.zhou.yadmin.tools.service.EmailService;
import com.zhou.yadmin.tools.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/16 2:19
 */
@RestController
@RequestMapping("api/code")
public class VerificationCodeController
{
    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/resetEmail")
    public ResponseEntity sendEmail(@RequestBody VerificationCode code) throws Exception
    {
        code.setScenes(FrontConstant.RESET_MAIL);
        EmailDto email = verificationCodeService.sendEmail(code);
        emailService.send(email, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/email/resetPass")
    public ResponseEntity resetPass(HttpServletRequest request, String email) throws Exception
    {
        VerificationCode code = new VerificationCode();
        code.setType("email");
        code.setValue(email);
        code.setScenes(FrontConstant.RESET_MAIL);
        EmailDto emailDto = verificationCodeService.sendEmail(code);
        emailService.send(emailDto, emailService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/validated")
    public ResponseEntity validated(VerificationCode code)
    {
        verificationCodeService.validated(code);
        return new ResponseEntity(HttpStatus.OK);
    }
}
