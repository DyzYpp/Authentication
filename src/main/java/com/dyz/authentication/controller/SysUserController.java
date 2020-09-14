package com.dyz.authentication.controller;

import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.Vo.PageVo;
import com.dyz.authentication.entity.Vo.SysUserVo;
import com.dyz.authentication.service.SysUserService;
import com.dyz.authentication.util.ResultUtil.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @ClassName SysUserController
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description 用户管理Controller层
 */
@RestController
@RequestMapping(value = "/user")
public class SysUserController {

    @Autowired
    SysUserService userService;

    /*
     * @Description 注册，添加用户
     * @param sysUserVO
     **/
    @PostMapping(value = "/register")
    public AjaxResult registerUser(@RequestBody() SysUserVo sysUserVO){
        return userService.registerUser(sysUserVO);
    }
    
    /*
     * @Description 查询用户列表
     * @param pageVo
     **/
    @GetMapping
    public AjaxResult getList(@RequestBody PageVo pageVo){
        List<SysUser> list = userService.getUserByPage(pageVo);
        return AjaxResult.success(list);
    }
    
    /*
     * @Description 修改用户
     * @param sysUserVO 
     **/
    @PutMapping
    public AjaxResult update(@RequestBody SysUserVo sysUserVO){
        final boolean update = userService.updateUser(sysUserVO);
        return update == true ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }
    
    /*
     * @Description 根据id查询用户
     * @param id 
     **/
    @GetMapping("/{id}")
    public AjaxResult getUserById(@PathVariable("id") String id){
        SysUser user = userService.getById(id);
        return AjaxResult.success(user);
    }

    /*
     * @Description 删除用户
     * @param ids
     **/
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<String> ids){
        boolean delete = userService.delete(ids);
        return delete == true ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 重置密码
     * @param sysUserVo 
     **/
    @PostMapping("/resetPassWord")
    public AjaxResult resetPassword(@RequestBody SysUserVo sysUserVo){
        boolean b = userService.resetPassword(sysUserVo);
        return b == true ? AjaxResult.success() : AjaxResult.error();
    }
}
