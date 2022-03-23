package com.dyz.authentication.configuration.authentication.requestAuthentication.handler;

import com.dyz.authentication.configuration.authentication.exception.MyAccessDeniedException;
import com.dyz.authentication.entity.SysApiInterface;
import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.SysUserOperationLog;
import com.dyz.authentication.service.SysApiInterfaceService;
import com.dyz.authentication.service.SysUserOperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName DynamicPermission
 * @Author Duan yuzhe
 * @Date 2020/8/30
 * @description 判断是否拥有接口访问权限
 */
@Component
public class DynamicPermission {

    @Autowired
    SysApiInterfaceService apiInterfaceService;

    @Autowired
    SysUserOperationLogService userOperationLogService;

    /**
     * 验证请求的api接口是否存在于该用户拥有的api权限列表中
     */
    public boolean checkPermission(HttpServletRequest request, Authentication authentication) {
        return true;
        // 创建操作日志对象
//        SysUserOperationLog operationLog = new SysUserOperationLog();
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserDetails){
//            String username = ((UserDetails) principal).getUsername();
//            operationLog.setOperationUser(username);
//            // 根据用户名查询用户可以访问的API接口
//            List<SysApiInterface> list = apiInterfaceService.getApiInterfaceListByUserName(username);
//            //请求方式
//            String requestMethod = request.getMethod();
//            operationLog.setRequestMethod(requestMethod);
//            //请求的uri
//            String requestUri = request.getRequestURI();
//            operationLog.setRequestUri(requestUri);
//            //使用stream流进行判断
//            AntPathMatcher antPathMatcher = new AntPathMatcher();
//            boolean anyMatch = list.stream().anyMatch(item -> {
//                boolean match = antPathMatcher.match(item.getApiUrl(), requestUri);
//                String dbMethod = item.getApiMethod();
//                int index = dbMethod.indexOf(requestMethod);
//                return match && index != -1;
//            });
//            if (anyMatch){
//                operationLog.setSucceed("成功");
//                this.userOperationLogService.save(operationLog);
//                return anyMatch;
//            } else {
//                operationLog.setSucceed("失败");
//                operationLog.setRequestDescription("没有访问该API的权限");
//                this.userOperationLogService.save(operationLog);
//                throw  new MyAccessDeniedException("您没有访问该API的权限！");
//            }
//        } else {
//            throw new MyAccessDeniedException("不是UserDetail类型");
//        }
    }
}

























