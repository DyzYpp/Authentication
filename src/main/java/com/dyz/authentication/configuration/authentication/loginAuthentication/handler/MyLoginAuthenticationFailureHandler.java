package com.dyz.authentication.configuration.authentication.loginAuthentication.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.dyz.authentication.configuration.ResponseResult.JSONResponseToWeb;
import com.dyz.authentication.service.SysLoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyLoginAuthenticationFailureHandler
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 登录失败处理
 */
@Component
public class MyLoginAuthenticationFailureHandler extends JSONResponseToWeb implements AuthenticationFailureHandler {

    @Autowired
    SysLoginLogService loginLogService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        R<String>  data = R.failed("登录失败"+e.getMessage());
        this.WriteJSON(httpServletRequest,httpServletResponse,data);
    }
}
