package com.dyz.authentication.configuration.authentication.loginAuthentication.handler;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.R;
import com.dyz.authentication.configuration.ResponseResult.JSONResponseToWeb;
import com.dyz.authentication.entity.SysLoginLog;
import com.dyz.authentication.entity.Vo.SysMenuVo;
import com.dyz.authentication.entity.auth.AuthUser;
import com.dyz.authentication.service.SysLoginLogService;
import com.dyz.authentication.service.SysMenuService;
import com.dyz.authentication.util.IpPathUtil;
import com.dyz.authentication.util.JwtTokenUtil;
import com.dyz.authentication.util.RedisTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName MyLoginAuthenticationSuccessHandler
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 登录成功后的操作
 */
@Component
public class MyLoginAuthenticationSuccessHandler extends JSONResponseToWeb implements AuthenticationSuccessHandler {

    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    SysMenuService menuService;

    @Autowired
    SysLoginLogService loginLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 获取账户信息
        AuthUser authUser = (AuthUser)authentication.getPrincipal();
        // 将账户信息存储到SecurityContextHolder中
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = this.getToken(authUser);
        List<SysMenuVo> menuList = null;
        // 加载前端菜单
        try{
             menuList = menuService.getMenuListByUserName(authUser.getUsername());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        // 添加登录日志
        SysLoginLog loginLog = new SysLoginLog(authUser.getUsername(), IpPathUtil.getIp(),"成功");
        this.loginLogService.save(loginLog);
        //返回数据给前端
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("username",authUser.getUsername());
        resultMap.put("authentication",authUser.getAuthorities());
        resultMap.put("menus",menuList);
        resultMap.put("token",token);
        R<Map<String,Object>> data =  R.ok(resultMap);
        this.WriteJSON(httpServletRequest,httpServletResponse,data);
    }

    /*
     * @Description 创建token
     * @param userDetails 
     * @return java.lang.String
     **/
    public String getToken(AuthUser authUser){
        String redisToken = redisTemplateUtil.getItem(authUser.getUsername());
        String token;
        if (StringUtils.checkValNull(redisToken)){
            token = jwtTokenUtil.generateToken(authUser);
            redisTemplateUtil.setItemWithExpireTime(authUser.getUsername(),token,redisTemplateUtil.REDIS_EXPIRE_TIME);
        } else {
            token = redisToken;
        }
        return token;
    }
}
