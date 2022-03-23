package com.dyz.authentication.configuration.authentication.securityCore;

import com.dyz.authentication.configuration.authentication.loginAuthentication.coreFilter.MyUserNamePassWordAuthenticationFilter;
import com.dyz.authentication.configuration.authentication.loginAuthentication.handler.MyLoginAuthenticationFailureHandler;
import com.dyz.authentication.configuration.authentication.loginAuthentication.handler.MyLoginAuthenticationSuccessHandler;
import com.dyz.authentication.configuration.authentication.loginAuthentication.handler.MyLogoutHandler;
import com.dyz.authentication.configuration.authentication.loginAuthentication.handler.MyLogoutSuccessHandler;
import com.dyz.authentication.configuration.authentication.requestAuthentication.coreFilter.MyOncePerRequestFilter;
import com.dyz.authentication.configuration.authentication.requestAuthentication.handler.DynamicPermission;
import com.dyz.authentication.configuration.authentication.requestAuthentication.handler.MyAccessDeniedHandler;
import com.dyz.authentication.configuration.authentication.requestAuthentication.handler.MyAuthenticationEntryPoint;
import com.dyz.authentication.entity.auth.AuthUser;
import com.dyz.authentication.service.impl.AuthenticationImpl;
import com.dyz.authentication.util.BCryptPasswordEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

/**
 * @ClassName WebSecurityCoreConfig
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 通过springsecurity进行身份认证入口
 */
@Configuration
public class WebSecurityCoreConfig extends WebSecurityConfigurerAdapter {

    // 登录认证成功处理
    @Autowired
    private MyLoginAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    //登录认证失败处理
    @Autowired
    private MyLoginAuthenticationFailureHandler myAuthenticationFailureHandler;

    //token拦截验证处理
    @Autowired
    private MyOncePerRequestFilter myOncePerRequestFilter;

    //身份校验失败处理
    @Autowired
    private MyAuthenticationEntryPoint myAuthenticationEntryPoint;

    //权限校验处理
    @Autowired
    private MyAccessDeniedHandler myAccessDeniedHandler;

    //退出处理
    @Autowired
    private MyLogoutHandler myLogoutHandler;

    //成功退出处理
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Autowired
    AuthenticationImpl authentication;

    @Autowired
    @Qualifier("authenticationImpl")
    private UserDetailsService userDetailsService;

    // 密码加密/验证
    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    //自定义拦截请求规则
    @Autowired
    DynamicPermission dynamicPermission;

    /**
     * 请求拦截配置核心方法
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //第1步：解决跨域问题。cors 预检请求放行,让Spring security 放行所有preflight request（cors 预检请求） OPTIONS预请求
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll();
        //第2步：让Security永远不会创建HttpSession，它不会使用HttpSession来获取SecurityContext
        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().cacheControl();
        //第3步：请求权限配置
        //放行注册API请求，其它任何请求都必须经过身份验证.
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST,"/user/register").permitAll()
//                .antMatchers(HttpMethod.GET,"/user/{id}").access("hasIpAddress('192.168.0.116') or hasAuthority('ROLE_ADMIN') and has")
//                //ROLE_ADMIN可以操作任何事情
//                .antMatchers("/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().access("@dynamicPermission.checkPermission(request,authentication)");
        //第4步：拦截账号、密码。覆盖 UsernamePasswordAuthenticationFilter过滤器
        http.addFilterAt(myUserNamePassWordAuthenticationFilter() , UsernamePasswordAuthenticationFilter.class);
        //第5步：拦截token，并检测。在 UsernamePasswordAuthenticationFilter 之前添加 JwtAuthenticationTokenFilter
        http.addFilterBefore(myOncePerRequestFilter, UsernamePasswordAuthenticationFilter.class);
        //第6步：处理异常情况：认证失败和权限不足
        http.exceptionHandling().authenticationEntryPoint(myAuthenticationEntryPoint).accessDeniedHandler(myAccessDeniedHandler);
        //第7步：登录,因为使用前端发送JSON方式进行登录，所以登录模式不设置也是可以的。
        http.formLogin();
        //第8步：退出
        http.logout().addLogoutHandler(myLogoutHandler).logoutSuccessHandler(myLogoutSuccessHandler);
    }

    /**
     * 根据用户名加载用户信息以及设置密码编码格式
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoderUtil);
    }

    @Bean
    MyUserNamePassWordAuthenticationFilter myUserNamePassWordAuthenticationFilter() throws Exception {
        MyUserNamePassWordAuthenticationFilter filter = new MyUserNamePassWordAuthenticationFilter();
        //成功后处理
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler);
        //失败后处理
        filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * 登录成功后，从security中获取用户名称
     */
    public static String getUserNameFromSecurity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = null;
        try {
            authUser = (AuthUser) authentication.getPrincipal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return authUser != null ? authUser.getUsername() : "前端测试";
    }
}
