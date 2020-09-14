package com.dyz.authentication.controller;

import com.dyz.authentication.entity.SysLoginLog;
import com.dyz.authentication.entity.SysUserOperationLog;
import com.dyz.authentication.service.SysLoginLogService;
import com.dyz.authentication.service.SysUserOperationLogService;
import com.dyz.authentication.util.ResultUtil.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName Sys
 * @Author
 * @Date 2020/9/11
 * @description
 */
@RestController
@RequestMapping("/log")
public class SysLogController {

    @Autowired
    SysLoginLogService loginLogService;

    @Autowired
    SysUserOperationLogService userOperationLogService;

    /*
     * @Description 查询登录日志列表
     * @param  
     **/
    @GetMapping("/loginLog")
    public AjaxResult getLoginLog(){
        List<SysLoginLog> list = this.loginLogService.list();
        return AjaxResult.success(list);
    }

    /*
     * @Description 查询操作日志列表
     * @param
     **/
    @GetMapping("/operationLog")
    public AjaxResult getOperationLog(){
        List<SysUserOperationLog> list = this.userOperationLogService.list();
        return AjaxResult.success(list);
    }

    /*
     * @Description 删除登录日志
     * @param ids
     **/
    @DeleteMapping("/loginLog")
    public AjaxResult deleteLoginLog(@RequestParam("logIds") List<String> ids){
        boolean b = this.loginLogService.removeByIds(ids);
        return b == true ? AjaxResult.success("") : AjaxResult.error();
    }

    /*
     * @Description 删除用户操作日志
     * @param ids
     **/
    @DeleteMapping("/operationLog")
    public AjaxResult deleteOperationLog(@RequestParam("operationIds") List<String> ids){
        boolean b = this.userOperationLogService.removeByIds(ids);
        return b == true ? AjaxResult.success("") : AjaxResult.error();
    }



}
