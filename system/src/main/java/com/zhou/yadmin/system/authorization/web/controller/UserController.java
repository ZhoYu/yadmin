/**
 * <p>
 * 文件名称:    UserController
 * </p>
 */
package com.zhou.yadmin.system.authorization.web.controller;

import java.util.Map;

import com.google.common.collect.Maps;
import com.zhou.yadmin.common.constants.FrontConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.utils.WebUtils;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.authorization.domain.User;
import com.zhou.yadmin.system.authorization.dto.UserDto;
import com.zhou.yadmin.system.authorization.service.UserService;
import com.zhou.yadmin.system.core.security.JwtUser;
import com.zhou.yadmin.system.core.utils.EncryptUtils;
import com.zhou.yadmin.tools.domain.Picture;
import com.zhou.yadmin.tools.domain.VerificationCode;
import com.zhou.yadmin.tools.service.PictureService;
import com.zhou.yadmin.tools.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/29 21:07
 */
@RestController
@RequestMapping("api/users")
public class UserController
{
    private static final String ENTITY_NAME = "user";

    @Autowired
    private UserService userService;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUser(@PathVariable Long id)
    {
        return new ResponseEntity(userService.findById(id), HttpStatus.OK);
    }

    @Log("查询用户")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_SELECT')")
    public ResponseEntity getUsers(UserDto userDTO, Pageable pageable)
    {
        return new ResponseEntity(userService.queryAll(userDTO, pageable), HttpStatus.OK);
    }

    @Log("新增用户")
    @PostMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_CREATE')")
    public ResponseEntity create(@Validated @RequestBody User resources)
    {
        if (resources.getId() != null)
        {
            throw BadRequestException.newExceptionBySystem("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return new ResponseEntity(userService.create(resources), HttpStatus.CREATED);
    }

    @Log("修改用户")
    @PutMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_EDIT')")
    public ResponseEntity update(@Validated @RequestBody User resources)
    {
        if (resources.getId() == null)
        {
            throw BadRequestException.newExceptionBySystem(ENTITY_NAME + " ID Can not be empty");
        }
        userService.update(resources);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Log("删除用户")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER_ALL','USER_DELETE')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        userService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 验证密码
     *
     * @param pass
     *
     * @return
     */
    @GetMapping(value = "/validPass/{pass}")
    public ResponseEntity validPass(@PathVariable String pass)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        Map<String, Object> map = Maps.newHashMap();
        map.put("status", 200);
        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(pass)))
        {
            map.put("status", 400);
        }
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 修改密码
     *
     * @param pass
     *
     * @return
     */
    @GetMapping(value = "/updatePass/{pass}")
    public ResponseEntity updatePass(@PathVariable String pass)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        if (jwtUser.getPassword().equals(EncryptUtils.encryptPassword(pass)))
        {
            throw BadRequestException.newExceptionBySystem("新密码不能与旧密码相同");
        }
        userService.updatePass(jwtUser, EncryptUtils.encryptPassword(pass));
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改头像
     *
     * @param file
     *
     * @return
     */
    @PostMapping(value = "/updateAvatar")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        Picture picture = pictureService.upload(file, jwtUser.getUsername());
        userService.updateAvatar(jwtUser, picture.getUrl());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 修改邮箱
     *
     * @param user
     * @param user
     *
     * @return
     */
    @PostMapping(value = "/updateEmail/{code}")
    public ResponseEntity updateEmail(@PathVariable String code, @RequestBody User user)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        if (!jwtUser.getPassword().equals(EncryptUtils.encryptPassword(user.getPassword())))
        {
            throw BadRequestException.newExceptionBySystem("密码错误");
        }
        VerificationCode verificationCode = new VerificationCode(code, FrontConstant.RESET_MAIL, "email", user.getEmail());
        verificationCodeService.validated(verificationCode);
        userService.updateEmail(jwtUser, user.getEmail());
        return new ResponseEntity(HttpStatus.OK);
    }
}
