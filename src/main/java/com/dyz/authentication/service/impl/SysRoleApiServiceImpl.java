package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysRoleApi;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysRoleApiMapper;
import com.dyz.authentication.service.SysRoleApiService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName SysRoleApiServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/9
 * @description
 */
@Service("roleApiService")
public class SysRoleApiServiceImpl extends ServiceImpl<SysRoleApiMapper, SysRoleApi> implements SysRoleApiService {

    /*
     * @Description 更新角色api权限
     * @param roleId
	 * @param sysRoleAndPermissionVos
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean updateRoleApi(String roleId, SysRoleAndPermissionVo[] sysRoleAndPermissionVos) {
        if (roleId != null && !roleId.equals("")){
            QueryWrapper<SysRoleApi> queryWrapper = new QueryWrapper<SysRoleApi>().eq("role_id", roleId);
            boolean remove = this.remove(queryWrapper);
            Set<SysRoleApi> roleApiSet = new HashSet<>();
            if (remove){
                for (SysRoleAndPermissionVo sysRoleAndPermissionVo : sysRoleAndPermissionVos) {
                    SysRoleApi roleApi = new SysRoleApi();
                    roleApi.setApiId(sysRoleAndPermissionVo.getId());
                    roleApi.setRoleId(roleId);
                    roleApiSet.add(roleApi);
                }
                boolean b = this.saveBatch(roleApiSet);
                if (b);return true;
            }
        }
        return false;
    }
}
