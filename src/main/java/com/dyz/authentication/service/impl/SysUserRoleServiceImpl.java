package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysUserRole;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysUserRoleMapper;
import com.dyz.authentication.service.SysUserRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SysUserRoleServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/8
 * @description
 */
@Service("userRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    /*
     * @Description 更新用户角色权限
     * @param roleId
     * @param sysRoleAndPermissionVos
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean updateRoleUser(String roleId, SysRoleAndPermissionVo[] sysRoleAndPermissionVos) {
        if (roleId != null && !roleId.equals("")){
            boolean remove = this.remove(new QueryWrapper<SysUserRole>().eq("role_id", roleId));
            if (remove){
                Set<SysUserRole> userRoles = new HashSet<>();
                for (SysRoleAndPermissionVo sysRoleAndPermissionVo : sysRoleAndPermissionVos) {
                    SysUserRole userRole = new SysUserRole();
                    userRole.setUserId(sysRoleAndPermissionVo.getId());
                    userRole.setRoleId(roleId);
                    userRoles.add(userRole);
                }
                boolean b = this.saveBatch(userRoles);
                if (b); return true;
            }
        }
        return false;
    }
}
