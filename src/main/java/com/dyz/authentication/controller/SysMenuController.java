package com.dyz.authentication.controller;

import com.dyz.authentication.entity.SysMenu;
import com.dyz.authentication.entity.VO.SysMenuVo;
import com.dyz.authentication.service.SysMenuService;
import com.dyz.authentication.service.SysRoleService;
import com.dyz.authentication.util.SnowFlake;
import com.dyz.authentication.util.returnResult.AjaxResult;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName SysMenuController
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description 菜单管理Controller层
 */
@RestController
@RequestMapping(value = "/menu")
public class SysMenuController {

    @Autowired
    SysMenuService menuService;

    /*
     * @Description 添加菜单
     * @param sysMenu 
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @PostMapping
    public AjaxResult insertMenu(@RequestBody SysMenu sysMenu){
        boolean save = menuService.save(sysMenu);
        return save == true ? AjaxResult.success("添加成功") : AjaxResult.error("添加失败");
    }
    
    /*
     * @Description 查询所有菜单
     * @param  
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @GetMapping
    public AjaxResult getAllMenuList(){
        List<SysMenuVo> allMenuList = menuService.getAllMenuList();
        return AjaxResult.success(allMenuList);
    }

    /*
     * @Description 通过id查询菜单
     * @param id 
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @GetMapping("/{id}")
    public AjaxResult getMenuById(@PathVariable("id") String id){
        SysMenu menu = menuService.getById(id);
        return AjaxResult.success(menu);
    }
    
    /*
     * @Description 修改菜单
     * @param sysMenu 
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @PutMapping
    public AjaxResult update(@RequestBody SysMenu sysMenu){
        boolean b = menuService.updateById(sysMenu);
        return b == true ? AjaxResult.success() : AjaxResult.error();
    }

    /*
     * @Description 删除菜单
     * @param ids
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @DeleteMapping
    public AjaxResult delete(@RequestParam("idList") List<String> ids){
        boolean remove = menuService.delete(ids);
        return remove == true ? AjaxResult.success() : AjaxResult.error();
    }

    

  /*  @GetMapping("/{username}")
    public AjaxResult getMenuListByUserName(@PathVariable("username") String username){
        List<SysMenu> menuListByUserName = menuService.getMenuListByUserName(username);
        return AjaxResult.success(menuListByUserName);
    }*/
}
