package com.dyz.authentication.service.impl;

import com.dyz.authentication.entity.SysRole;
import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.auth.AuthUser;
import com.dyz.authentication.service.SysRoleService;
import com.dyz.authentication.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName authentication
 * @Author Daun yuzhe
 * @Date 2020/9/3
 * @description 实现UserDetailsService 登录成功返回用户信息
 */
@Service
public class AuthenticationImpl implements UserDetailsService {

    @Autowired
    SysUserService userService;

    @Autowired
    SysRoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUserName(s);
        if (user == null){
            throw new UsernameNotFoundException("这个用户不存在"+s);
        } else {
            List<SysRole> roles = roleService.getRoleListByUserName(s);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (SysRole role : roles) {
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            }
            return new AuthUser(user.getUserName(),user.getPassword(),user.getStatus(),authorities);
        }
    }
}
