/**
 * <p>
 * 文件名称:    WebSecurityConfig
 * </p>
 */
package com.zhou.yadmin.system.core.config;

import com.zhou.yadmin.system.core.security.JwtAuthenticationEntryPoint;
import com.zhou.yadmin.system.core.security.JwtAuthorizationTokenFilter;
import com.zhou.yadmin.system.core.service.impl.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 20:55
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
    /**
     * 未授权处理
     */
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    /**
     * 自定义基于JWT的安全过滤器
     */
    @Autowired
    private JwtAuthorizationTokenFilter authenticationTokenFilter;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.auth.path}")
    private String authenticationPath;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoderBean());
    }

    @Bean
    public PasswordEncoder passwordEncoderBean()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception
    {
        httpSecurity.csrf().disable()// 禁用 CSRF
          .exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // 授权异常
          .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 不创建会话
          .and().authorizeRequests().antMatchers("/auth/**").permitAll().antMatchers("/websocket/**").permitAll().antMatchers("/druid/**").anonymous()
          .antMatchers("/api/aliPay/return").anonymous().antMatchers("/api/aliPay/notify").anonymous() // 支付宝回调
          .antMatchers("/swagger-ui.html").anonymous().antMatchers("/swagger-resources/**").anonymous().antMatchers("/webjars/**").anonymous()
          .antMatchers("/*/api-docs").anonymous() // swagger
          .antMatchers("/test/**").anonymous() // 测试入口
          .antMatchers(HttpMethod.OPTIONS, "/**").anonymous().anyRequest().authenticated(); // 所有请求都需要认证
        httpSecurity.addFilterBefore(authenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // AuthenticationTokenFilter will ignore the below paths
        web.ignoring().antMatchers(HttpMethod.POST, authenticationPath)
          // allow anonymous resource requests
          .and().ignoring().antMatchers(HttpMethod.GET, "/*.html", "/**/*.html", "/**/*.css", "/**/*.js");
    }
}
