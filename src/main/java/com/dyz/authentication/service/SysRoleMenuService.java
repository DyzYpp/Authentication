package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysRoleMenu;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;

/**
 * @ClassName SysRoleMenuService
 * @Author Duan yuzhe
 * @Date 2020/9/8
 * @description
 */
public interface SysRoleMenuService extends IService<SysRoleMenu> {

    /**
     * 更新角色菜单权限
     */
    boolean updateRoleMenu(String roleId, SysRoleAndPermissionVo[] sysRoleAndPermissionVos);
}
