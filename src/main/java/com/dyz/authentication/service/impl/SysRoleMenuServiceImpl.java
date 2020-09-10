package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysRoleMenu;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysRoleMenuMapper;
import com.dyz.authentication.service.SysRoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SysRoleMenuServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/8
 * @description
 */
@Service("roleMenuService")
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    /*
     * @Description 更新角色菜单权限
     * @param roleId
	 * @param sysRoleAndPermissionVos
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean updateRoleMenu(String roleId, SysRoleAndPermissionVo[] sysRoleAndPermissionVos) {
        if (roleId != null && !roleId.equals("")){
            boolean remove = this.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
            if (remove){
                Set<SysRoleMenu> roleMenus = new HashSet<>();
                for (SysRoleAndPermissionVo sysRoleAndPermissionVo : sysRoleAndPermissionVos) {
                    SysRoleMenu roleMenu = new SysRoleMenu();
                    roleMenu.setMenuId(sysRoleAndPermissionVo.getId());
                    roleMenu.setRoleId(roleId);
                    roleMenus.add(roleMenu);
                }
                boolean b = this.saveBatch(roleMenus);
                if (b); return true;
            }
        }
        return false;
    }
}
