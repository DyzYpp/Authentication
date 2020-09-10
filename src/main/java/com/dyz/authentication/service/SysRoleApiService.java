package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysRoleApi;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;

import java.util.List;

/**
 * @interface SysRoleApiService
 * @Author Duan yuzhe
 * @Date 2020/9/9
 * @description
 */
public interface SysRoleApiService extends IService<SysRoleApi> {

    /**
     * 更新角色api权限
     */
    boolean updateRoleApi(String roleId, SysRoleAndPermissionVo[] sysRoleAndPermissionVos);

}
