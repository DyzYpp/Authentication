package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysApiInterface;
import com.dyz.authentication.entity.SysRoleApi;
import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.SysUserRole;
import com.dyz.authentication.entity.Vo.SysApiInterfaceVo;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysApiInterfaceMapper;
import com.dyz.authentication.mapper.SysRoleApiMapper;
import com.dyz.authentication.service.SysApiInterfaceService;
import com.dyz.authentication.service.SysRoleApiService;
import com.dyz.authentication.service.SysUserRoleService;
import com.dyz.authentication.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName SysApiInterfaceServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description api接口实现类
 */
@Service
public class SysApiInterfaceServiceImpl extends ServiceImpl<SysApiInterfaceMapper,SysApiInterface> implements SysApiInterfaceService {

    @Autowired
    SysApiInterfaceMapper sysApiInterfaceMapper;

    @Autowired
    SysRoleApiMapper roleApiMapper;

    @Autowired
    SysRoleApiService roleApiService;

    @Autowired
    SysUserService userService;

    @Autowired
    SysUserRoleService userRoleService;
    /*
     * @Description 根据用户名查询api接口权限
     * @param username 用户名称
     * @return java.util.List<com.dyz.authentication.entity.SysApiInterface>
     **/
    @Override
    public List<SysApiInterface> getApiInterfaceListByUserName(String username) {
        // 存放结果列表
        List<SysApiInterface> list = new ArrayList<>();
        SysUser user = this.userService.getUserByUserName(username);
        // 当前用户角色列表
        List<SysUserRole> userRoles = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));
        // 角色api全部列表
        List<SysRoleApi> roleApis = roleApiService.list();
        // 将用户可以访问的api接口放入list集合中
        for (SysUserRole userRole : userRoles) {
            for (SysRoleApi roleApi : roleApis) {
                if (userRole.getRoleId().equals(roleApi.getRoleId())){
                    SysApiInterface anInterface = this.getById(roleApi.getApiId());
                    list.add(anInterface);
                }
            }
        }
        return list;
    }

    /*
     * @Description 查询api列表
     * @param
     * @return java.util.List<com.dyz.authentication.entity.VO.SysApiInterfaceVo>
     **/
    @Override
    public List<SysApiInterfaceVo> getAllList() {
        List<SysApiInterfaceVo> resultList = new ArrayList<>();
        List<SysApiInterface> list = this.list();
        for (SysApiInterface sysApiInterface : list) {
            SysApiInterfaceVo sysApiInterfaceVo = new SysApiInterfaceVo();
            sysApiInterfaceVo.setApiId(sysApiInterface.getApiId());
            sysApiInterfaceVo.setParentName(null);
            sysApiInterfaceVo.setApiMethod(sysApiInterface.getApiMethod());
            sysApiInterfaceVo.setApiName(sysApiInterface.getApiName());
            sysApiInterfaceVo.setApiSort(sysApiInterface.getApiSort());
            sysApiInterfaceVo.setApiUrl(sysApiInterface.getApiUrl());
            sysApiInterfaceVo.setCreateTime(sysApiInterface.getCreateTime());
            sysApiInterfaceVo.setUpdateTime(sysApiInterface.getUpdateTime());
            sysApiInterfaceVo.setDescription(sysApiInterface.getDescription());
            sysApiInterfaceVo.setPid(sysApiInterface.getPid());
            resultList.add(sysApiInterfaceVo);
        }
        for (SysApiInterfaceVo sysApiInterfaceVo : resultList) {
            for (SysApiInterface sysApiInterface : list) {
                if (sysApiInterfaceVo.getPid() != null &&
                        sysApiInterfaceVo.getPid().equals(sysApiInterface.getApiId())){
                    sysApiInterfaceVo.setParentName(sysApiInterface.getApiName());
                }
            }
        }
        return resultList.stream().sorted(Comparator.comparing(SysApiInterfaceVo::getApiSort)).collect(Collectors.toList());
    }

    /*
     * @Description 根据roleId获取api列表
     * @param roleId
     * @return java.util.List<com.dyz.authentication.entity.VO.SysRoleAndPermissionVo>
     **/
    @Override
    public List<SysRoleAndPermissionVo> getApiListByRoleId(String roleId) {
        // api列表集合
        List<SysApiInterface> apiInterfaceList = this.list();
        // 角色API集合
        List<SysRoleApi> roleApiList = roleApiMapper.selectList(new QueryWrapper<SysRoleApi>().eq("role_id", roleId));
        return ListToHierarchyListService.listToHierarchyList(apiInterfaceList,roleApiList);
    }

    /*
     * @Description 删除api接口
     * @param ids
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean delete(List<String> ids) {
        if (ids != null){
            for (String id : ids) {
                List<SysRoleApi> list = roleApiService.list(new QueryWrapper<SysRoleApi>().eq("api_id", id));
                if (list.size() != 0){
                    roleApiService.remove(new QueryWrapper<SysRoleApi>().eq("api_id", id));
                }
            }
            boolean b = this.removeByIds(ids);
            if (b); return true;
        }
        return false;
    }
}
