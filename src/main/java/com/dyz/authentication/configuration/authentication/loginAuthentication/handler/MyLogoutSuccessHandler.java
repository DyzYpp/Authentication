package com.dyz.authentication.configuration.authentication.loginAuthentication.handler;

import com.baomidou.mybatisplus.extension.api.R;
import com.dyz.authentication.configuration.ResponseResult.JSONResponseToWeb;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 成功退出处理器
 */
@Component
public class MyLogoutSuccessHandler extends JSONResponseToWeb implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        R<String> data = R.ok("退出成功");
        super.WriteJSON(request,response,data);
    }
}
