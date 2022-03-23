package com.dyz.authentication.configuration.authentication.loginAuthentication.handler;

import com.dyz.authentication.configuration.ResponseResult.JSONResponseToWeb;
import com.dyz.authentication.util.JwtTokenUtil;
import com.dyz.authentication.util.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 退出Handler
 */
@Component
public class MyLogoutHandler extends JSONResponseToWeb implements LogoutHandler {

    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    private static final String tokenHeader = "Authorization";

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String headerToken = request.getHeader(tokenHeader);
        // 获取用户名
        String username = jwtTokenUtil.getUsernameFromToken(headerToken);
        // 从redis中删除该用户的相关存储信息
        redisTemplateUtil.remove(username);
        if (!StringUtils.isEmpty(headerToken)) {
            // 从securityContext中清除用户存储的相关信息
            SecurityContextHolder.clearContext();
        }
    }
}
