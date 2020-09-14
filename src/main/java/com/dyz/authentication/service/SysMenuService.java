package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysMenu;
import com.dyz.authentication.entity.Vo.SysMenuVo;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;

import java.util.List;

/**
 * @interface MenuService
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 根据用户名查询菜单
     * 控制菜单权限
     */
    List<SysMenuVo> getMenuListByUserName(String username);

    /**
     * 获取所有菜单
     */
    List<SysMenuVo> getAllMenuList();

    /**
     * 查询菜单列表中对应角色id包含roleId的菜单
     */
    List<SysRoleAndPermissionVo> getMenuListByRoleId(String roleId);

    /**
     * 删除api接口
     */
    boolean delete(List<String> ids);
}
