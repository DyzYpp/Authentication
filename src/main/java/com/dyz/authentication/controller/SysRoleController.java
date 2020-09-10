package com.dyz.authentication.controller;

import com.dyz.authentication.entity.SysRole;
import com.dyz.authentication.entity.VO.PageVo;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;
import com.dyz.authentication.service.*;
import com.dyz.authentication.util.returnResult.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @ClassName SysRoleController
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description 角色管理Controller层
 */
@RestController
@RequestMapping(value = "/role")
public class SysRoleController {

    @Autowired
    SysRoleService roleService;

    @Autowired
    SysApiInterfaceService apiInterfaceService;

    @Autowired
    SysUserService userService;

    @Autowired
    SysMenuService menuService;

    @Autowired
    SysRoleApiService roleApiService;

    @Autowired
    SysRoleMenuService roleMenuService;

    @Autowired
    SysUserRoleService userRoleService;

    /*
     * @Description 添加角色
     * @param sysRole
     **/
    @PostMapping
    public AjaxResult insertRole(@RequestBody SysRole sysRole) {
        if (sysRole != null) {
            boolean save = roleService.save(sysRole);
            return save == true ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
        }
        return AjaxResult.error("角色信息为空!");
    }

    /*
     * @Description 查询角色列表
     * @param
     **/
    @GetMapping
    public AjaxResult getRoleList(@RequestBody PageVo pageVo) {
        List<SysRole> list = roleService.getRoleByPage(pageVo);
        return AjaxResult.success(list);
    }

    /*
     * @Description 通过id查询角色信息
     * @param roleId 
     **/
    @GetMapping("/{id}")
    public AjaxResult getRoleById(@PathVariable("id") String id){
        SysRole role = roleService.getById(id);
        return AjaxResult.success(role);
    }

    /*
     * @Description 修改角色
     * @param sysRole
     **/
    @PutMapping
    public AjaxResult update(@RequestBody SysRole sysRole) {
        boolean b = roleService.updateById(sysRole);
        return b == true ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }

    /*
     * @Description 查询拥有角色id为roleId的api列表
     * @param roleId
     **/
    @GetMapping("/roleApi/{roleId}")
    public AjaxResult getApiListByRoleId(@PathVariable String roleId) {
        return AjaxResult.success(apiInterfaceService.getApiListByRoleId(roleId));
    }

    /*
     * @Description 修改角色api权限
     * @param roleId
	 * @param sysRoleAndPermissionVo
     **/
    @PostMapping("/roleApi/update")
    public AjaxResult updateRoleApi(@RequestParam("roleId") String roleId, @RequestBody SysRoleAndPermissionVo[] sysRoleAndPermissionVoList) {
        boolean b = roleApiService.updateRoleApi(roleId, sysRoleAndPermissionVoList);
        return b == true ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 查询拥有角色id为roleId的用户列表
     * @param id
     **/
    @GetMapping("/roleUser/{roleId}")
    public AjaxResult getUserListByRoleId(@PathVariable String roleId) {
        return AjaxResult.success(userService.getUserListByRoleId(roleId));
    }

    /*
     * @Description 修改角色api权限
     * @param roleId
     * @param sysRoleAndPermissionVo
     **/
    @PostMapping("/roleUser/update")
    public AjaxResult updateRoleUser(@RequestParam("roleId") String roleId, @RequestBody SysRoleAndPermissionVo[] sysRoleAndPermissionVoList) {
        boolean b = userRoleService.updateRoleUser(roleId, sysRoleAndPermissionVoList);
        return b == true ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 查询拥有角色id为roleId的菜单列表
     * @param id
     **/
    @GetMapping("/roleMenu/{roleId}")
    public AjaxResult getMenuListByRoleId(@PathVariable String roleId) {
        return AjaxResult.success(menuService.getMenuListByRoleId(roleId));
    }

    /*
     * @Description 修改角色菜单权限
     * @param roleId
     * @param sysRoleAndPermissionVo
     **/
    @PostMapping("/roleMenu/update")
    public AjaxResult updateRoleMenu(@RequestParam("roleId") String roleId, @RequestBody SysRoleAndPermissionVo[] sysRoleAndPermissionVoList) {
        boolean b = roleMenuService.updateRoleMenu(roleId, sysRoleAndPermissionVoList);
        return b == true ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 删除角色
     * @param ids
     **/
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<String> ids){
        boolean delete = roleService.delete(ids);
        return delete == true ? AjaxResult.success() : AjaxResult.error();
    }

}
