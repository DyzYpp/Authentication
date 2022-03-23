package com.dyz.authentication.configuration.authentication.loginAuthentication.coreFilter;

import com.dyz.authentication.configuration.authentication.exception.MyAuthenticationException;
import com.dyz.authentication.entity.SysLoginLog;
import com.dyz.authentication.service.SysLoginLogService;
import com.dyz.authentication.service.SysUserService;
import com.dyz.authentication.util.IpPathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName MyUserNamePassWordAuthenticationFilter
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 重写UsernamePasswordAuthenticationFilter过滤器
 */
public class MyUserNamePassWordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private SysUserService userService;

//    @Autowired
//    SysLoginLogService loginLogService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 创建登录日志对象
//        SysLoginLog loginLog = new SysLoginLog(IpPathUtil.getIp());
        // 判断请求传递参数的格式
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {

            // ObjectMapper可以将实体对象与json字符串、byte数组 list集合与json字符串 map与json字符串相互转换
            ObjectMapper objectMapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authentication = null;
            // 存放用户信息
            Map authenticationBean = null;
            //用try with resource，方便自动释放资源
            try (InputStream is = request.getInputStream()) {
                authenticationBean = objectMapper.readValue(is, Map.class);
            } catch (IOException e) {
                //将异常放到自定义的异常类中
                throw new MyAuthenticationException(e.getMessage());
            }
            try {
                if (!authenticationBean.isEmpty()){
                    // 获取账号密码
                    String userName = (String) authenticationBean.get(SPRING_SECURITY_FORM_USERNAME_KEY);
                    String passWord = (String) authenticationBean.get(SPRING_SECURITY_FORM_PASSWORD_KEY);
                    // 将用户名设置为登录用户人
//                    loginLog.setLoginUser(userName);
                    // 检查账号是否存在，密码是否正确
                    if (userService.checkLoginInfo(userName,passWord)){
                        authentication = new UsernamePasswordAuthenticationToken(userName,passWord);
                        setDetails(request,authentication);
                        return this.getAuthenticationManager().authenticate(authentication);
                    }
                }
            } catch (Exception e) {
//                loginLog.setSucceed("失败");
//                loginLog.setMessage(e.getMessage());
//                this.loginLogService.save(loginLog);
                throw new MyAuthenticationException(e.getMessage());
            }
            return null;
        } else {
            return this.attemptAuthentication(request, response);
        }
    }
}
