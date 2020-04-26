/**
 * <p>
 * 文件名称:    AuthenticationController
 * </p>
 */
package com.zhou.yadmin.system.core.web.controller;

import javax.servlet.http.HttpServletRequest;

import com.zhou.yadmin.common.utils.WebUtils;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.system.core.security.AuthenticationToken;
import com.zhou.yadmin.system.core.security.AuthorizationUser;
import com.zhou.yadmin.system.core.security.JwtTokenHelper;
import com.zhou.yadmin.system.core.security.JwtUser;
import com.zhou.yadmin.system.core.utils.EncryptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 授权、根据token获取用户详细信息
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:11
 */
@RestController
@RequestMapping("auth")
public class AuthenticationController
{
    // @Value("${jwt.header}")
    // private String tokenHeader;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    /**
     * 登录授权
     *
     * @param authorizationUser
     *
     * @return
     */
    @Log("用户登录")
    @PostMapping(value = "${jwt.auth.path}")
    public ResponseEntity<?> authenticationLogin(@RequestBody AuthorizationUser authorizationUser)
    {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authorizationUser.getUsername());
        if (!userDetails.getPassword().equals(EncryptUtils.encryptPassword(authorizationUser.getPassword())))
        {
            throw new AccountExpiredException("密码错误");
        }
        if (!userDetails.isEnabled())
        {
            throw new AccountExpiredException("账号已停用，请联系管理员");
        }
        // 生成令牌
        final String token = jwtTokenHelper.generateToken(userDetails);
        // 返回 token
        return ResponseEntity.ok(new AuthenticationToken(token));
    }

    /**
     * 获取用户信息
     *
     * @param request
     *
     * @return
     */
    @GetMapping(value = "${jwt.auth.account}")
    public ResponseEntity getUserInfo(HttpServletRequest request)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(userDetails.getUsername());
        return ResponseEntity.ok(jwtUser);
    }
}
