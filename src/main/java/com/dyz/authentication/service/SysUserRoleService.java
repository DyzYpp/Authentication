package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysUserRole;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;

/**
 * @interface SysUserRoleService
 * @Author Duan yuzhe
 * @Date 2020/9/8
 */
public interface SysUserRoleService extends IService<SysUserRole> {

    /**
     * 更新用户角色权限
     */
    boolean updateRoleUser(String roleId,SysRoleAndPermissionVo[] sysRoleAndPermissionVos);
}
