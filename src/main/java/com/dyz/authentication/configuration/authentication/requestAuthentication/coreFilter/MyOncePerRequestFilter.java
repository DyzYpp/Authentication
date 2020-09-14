package com.dyz.authentication.configuration.authentication.requestAuthentication.coreFilter;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.api.IErrorCode;
import com.baomidou.mybatisplus.extension.api.R;
import com.dyz.authentication.configuration.ResponseResult.JSONResponseToWeb;
import com.dyz.authentication.util.JwtTokenUtil;
import com.dyz.authentication.util.RedisTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName MyOncePerRequestFilter
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description token拦截过滤器
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RedisTemplateUtil redisTemplateUtil;

    private final static String TOKEN_KEY = "Authorization";

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 从请求头中获取token
        String token = this.getTokenFromRequestHeader(request);
        try {
            if (StringUtils.checkValNotNull(token)){
                //2. 从token中获取用户名
                String username = jwtTokenUtil.getUsernameFromToken(token);
                // 如果用户名为null，说明jwt中token已经过期，需重新登录
                if (StringUtils.checkValNull(username)){
                    throw new ServletException("身份凭证已经过期，请重新登录。");
                }
                //3. 通过用户信息得到UserDetails
                UserDetails userDetails = this.getUserDetails(username, request);
                //4. 验证并更新数据
                this.validateAndUpdate(username,token,userDetails,request);
            }
            //5. 过滤链
            filterChain.doFilter(request, response);
        } catch (Exception e){
            JSONResponseToWeb jsonResponseToWeb = new JSONResponseToWeb();
            //标记为未登录状态，前端统一拦截，只要是-200 就属于未登录状态
            R<String> data = R.restResult(null, new IErrorCode() {
                @Override
                public long getCode() {
                    return -200;
                }

                @Override
                public String getMsg() {
                    return e.getMessage();
                }
            });
            //重新输出给前端信息
            jsonResponseToWeb.WriteJSON(request, response, data);
        }
    }

    /**
     * 从请求头中获取token
     */
    String getTokenFromRequestHeader(HttpServletRequest request){
        String header = request.getHeader(TOKEN_KEY);
        String token = null;
        if (StringUtils.checkValNotNull(header)){
            token = header.replace("Bearer", "").trim();
        }
        return token;
    }

    /**
     * 从认证容器中拿到用户实例
     */
    UserDetails getUserDetails(String username,HttpServletRequest request){
        UserDetails userDetails = null;
        // 判断用户不为空,且SecurityContextHolder授权信息为空
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            userDetails = userDetailsService.loadUserByUsername(username);
        }
        return userDetails;
    }

    /**
     * 将用户信息存储
     */
    AbstractAuthenticationToken getAuthenticationToken(UserDetails userDetails){
        // 将用户信息存储在Authentication中，方便后续校验
        AbstractAuthenticationToken authenticationToken = null;
        if (userDetails != null){
            authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        }
        return authenticationToken;
    }

    /**
     * 验证数据并更新
     */
    void validateAndUpdate(String username,String token, UserDetails userDetails, HttpServletRequest request) throws Exception {
        //验证小范围(redis)过期情况
        if (!this.checkRedisToken(username)){
            throw new ServletException("Token令牌过期，请重新登录。");
        }
        //验证大范围过期情况(周期)
        if (jwtTokenUtil.validateToken(token,userDetails)){
            // 刷新token
            String refreshToken = jwtTokenUtil.refreshToken(token);
            // 更新redis
            redisTemplateUtil.setItemWithExpireTime(username,refreshToken,JwtTokenUtil.EXPIRATION_TIME);
        }
        //将用户信息存储   authentication，方便后续校验
        AbstractAuthenticationToken authenticationToken = this.getAuthenticationToken(userDetails);
        // setDetails
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 将authentication存入 ThreadLocal中，方便后续获取用户信息
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * 验证redis中的token
     * @param userName
     */
    boolean checkRedisToken(String userName) {
        if(StringUtils.checkValNotNull(userName)) {
            Object redisToken = redisTemplateUtil.getItem(userName);
            return StringUtils.checkValNotNull(redisToken);
        }
        return false;
    }
}
