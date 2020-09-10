package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.*;
import com.dyz.authentication.entity.VO.SysMenuVo;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysMenuMapper;
import com.dyz.authentication.mapper.SysRoleMenuMapper;
import com.dyz.authentication.mapper.SysUserMapper;
import com.dyz.authentication.mapper.SysUserRoleMapper;
import com.dyz.authentication.service.SysMenuService;
import com.dyz.authentication.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MenuServiceImpl
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description
 */
@Service("menuService")
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    SysMenuMapper menuMapper;

    @Autowired
    SysRoleMenuMapper roleMenuMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysRoleMenuService roleMenuService;

    /*
     * @Description 根据用户名查询菜单
     * @param username
     * @return java.util.List<com.dyz.authentication.entity.SysMenu>
     **/
    @Override
    public List<SysMenu> getMenuListByUserName(String username) {
        List<SysMenu> menuList = new ArrayList<>();
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", username));
        List<SysUserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));
        for (SysUserRole userRole : userRoleList) {
            List<SysRoleMenu> roleMenuList = roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", userRole.getRoleId()));
            for (SysRoleMenu roleMenu : roleMenuList) {
                SysMenu menu = menuMapper.selectOne(new QueryWrapper<SysMenu>().eq("id", roleMenu.getMenuId()));
                menuList.add(menu);
            }
        }
        return menuList;
    }

    /*
     * @Description 查询所有菜单
     * @param
     * @return java.util.List<com.dyz.authentication.entity.VO.SysMenuVo>
     **/
    @Override
    public List<SysMenuVo> getAllMenuList() {
        List<SysMenuVo> resultList = new ArrayList<>();
        List<SysMenu> list = this.list();
        for (SysMenu sysMenu : list) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setMenuId(sysMenu.getMenuId());
            sysMenuVo.setParentName(null);
            sysMenuVo.setCreateTime(sysMenu.getCreateTime());
            sysMenuVo.setUpdateTime(sysMenu.getUpdateTime());
            sysMenuVo.setMenuUrl(sysMenu.getMenuUrl());
            sysMenuVo.setMenuSort(sysMenu.getMenuSort());
            sysMenuVo.setDescription(sysMenu.getDescription());
            sysMenuVo.setParentId(sysMenu.getParentId());
            sysMenuVo.setMenuName(sysMenu.getMenuName());
            resultList.add(sysMenuVo);
        }
        for (SysMenuVo sysMenuVo : resultList) {
            for (SysMenu menu : list) {
                if (sysMenuVo.getParentId() != null) {
                    if (sysMenuVo.getParentId().equals(menu.getMenuId())) {
                        sysMenuVo.setParentName(menu.getMenuName());
                    }
                }
            }
        }
        List<SysMenuVo> collect = resultList.stream().sorted(Comparator.comparing(SysMenu::getMenuSort)).collect(Collectors.toList());
        return collect;
    }

    /*
     * @Description 根据roleId查询菜单列表
     * @param roleId
     * @return java.util.List<com.dyz.authentication.entity.VO.SysRoleAndPermissionVo>
     **/
    @Override
    public List<SysRoleAndPermissionVo> getMenuListByRoleId(String roleId) {
        // 存储结果的集合
        List<SysRoleAndPermissionVo> resultList = new ArrayList<>();
        // 存储menu转换为SysRoleAndPermissionVo集合
        List<SysRoleAndPermissionVo> permissionVoList = new ArrayList<>();
        // 菜单集合
        List<SysMenu> menuList = this.list();
        // 角色菜单集合
        List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        // 将Menu对象集合转换为SysRoleAndPermissionVo对象集合
        for (SysMenu menu : menuList) {
            SysRoleAndPermissionVo permissionVo = new SysRoleAndPermissionVo(menu.getMenuId(), menu.getMenuName(), menu.getParentId());
            permissionVoList.add(permissionVo);
        }
        // 给当前角色权限范围内的菜单对象赋值roleId
        for (SysRoleAndPermissionVo permissionVo : permissionVoList) {
            for (SysRoleMenu roleMenu : roleMenus) {
                if (permissionVo.getId().equals(roleMenu.getMenuId())){
                    permissionVo.setRoleId(roleMenu.getRoleId());
                }
            }
        }
        // 父级菜单集合
        List<SysRoleAndPermissionVo> collect = permissionVoList.stream().filter(item -> item.getPid() == null).collect(Collectors.toList());
        // 根据parentId分类
        Map<String, List<SysRoleAndPermissionVo>> map = permissionVoList.stream().filter(item -> item.getPid() != null).collect(Collectors.groupingBy(SysRoleAndPermissionVo::getPid));
        if (collect.size() == map.size()){
            for (SysRoleAndPermissionVo vo : collect) {
                for (String s : map.keySet()) {
                    if (vo.getId().equals(s)){
                        vo.setChildrenList(map.get(s));
                    }
                }
                resultList.add(vo);
            }
        }
        return resultList;
    }
    
    /*
     * @Description 删除菜单
     * @param ids 
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean delete(List<String> ids) {
        if (ids != null){
            for (String id : ids) {
                boolean remove = roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
                if (remove != true); return false;
            }
            boolean b = this.removeByIds(ids);
            if (b); return true;
        }
        return false;
    }
}
