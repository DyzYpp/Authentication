package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.*;
import com.dyz.authentication.entity.Vo.PageVo;
import com.dyz.authentication.mapper.*;
import com.dyz.authentication.service.SysRoleApiService;
import com.dyz.authentication.service.SysRoleMenuService;
import com.dyz.authentication.service.SysRoleService;
import com.dyz.authentication.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName SysRoleServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description roles service实现类
 */
@Service("roleService")
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    SysRoleMapper roleMapper;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysUserRoleService userRoleService;

    @Autowired
    SysRoleMenuService roleMenuService;

    @Autowired
    SysRoleApiService roleApiService;

    /*
     * @Description 根据用户名查询用户角色
     * @param username 
     * @return java.util.List<com.dyz.authentication.entity.SysRole>
     **/
    @Override
    public List<SysRole> getRoleListByUserName(String username) {
        List<SysRole> list = new ArrayList<>();
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", username));
        List<SysUserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));
        for (SysUserRole userRole : userRoleList) {
            SysRole role = roleMapper.selectOne(new QueryWrapper<SysRole>().eq("id", userRole.getRoleId()));
            list.add(role);
        }
        List<SysRole> collect = list.stream().sorted(Comparator.comparing(SysRole::getRoleName)).collect(Collectors.toList());
        return collect;
    }


    /*
     * @Description 分页查询角色
     * @param pageVo 
     * @return java.util.List<com.dyz.authentication.entity.SysRole>
     **/
    @Override
    public List<SysRole> getRoleByPage(PageVo pageVo) {
        IPage<SysRole> res;
        List<SysRole> roles;
        if (pageVo.getCondition().equals("") || pageVo.getType().equals("")){
            res = roleMapper.selectPage(new Page<SysRole>(pageVo.getPage(),pageVo.getPageSize()),
                    new QueryWrapper<SysRole>().orderByAsc("role_name"));
        } else {
            res = roleMapper.selectPage(new Page<SysRole>(pageVo.getPage(), pageVo.getPageSize()),
                    new QueryWrapper<SysRole>().like(pageVo.getType(),
                            pageVo.getCondition()).orderByAsc("role_name"));
        }
        roles = res.getRecords();
        return roles;
    }

    /*
     * @Description 删除角色
     * @param ids
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean delete(List<String> ids) {
        if (ids != null){
            for (String id : ids) {
                List<SysRoleMenu> roleMenus = roleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
                if (roleMenus.size() != 0){
                     roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", id));
                }
                List<SysRoleApi> roleApis = roleApiService.list(new QueryWrapper<SysRoleApi>().eq("role_id", id));
                if (roleApis.size() != 0){
                    roleApiService.remove(new QueryWrapper<SysRoleApi>().eq("role_id", id));
                }
                List<SysUserRole> userRoles = userRoleService.list(new QueryWrapper<SysUserRole>().eq("role_id", id));
                if (userRoles.size() != 0){
                    userRoleService.remove(new QueryWrapper<SysUserRole>().eq("role_id", id));
                }
            }
            boolean b = this.removeByIds(ids);
            if (b); return true;
        }
        return false;
    }
}
