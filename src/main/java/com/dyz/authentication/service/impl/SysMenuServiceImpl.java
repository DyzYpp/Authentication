package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.*;
import com.dyz.authentication.entity.Vo.SysMenuVo;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;
import com.dyz.authentication.mapper.SysMenuMapper;
import com.dyz.authentication.mapper.SysRoleMenuMapper;
import com.dyz.authentication.mapper.SysUserMapper;
import com.dyz.authentication.mapper.SysUserRoleMapper;
import com.dyz.authentication.service.SysMenuService;
import com.dyz.authentication.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
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
    public List<SysMenuVo> getMenuListByUserName(String username) {
        long currentTimeMillis = System.currentTimeMillis();
        //  最终结果集合
        List<SysMenuVo> resultList = new ArrayList<>();
        // 将menu对象转换为menuVo对象的集合
        List<SysMenuVo> voList = new ArrayList<>();
        // 所有菜单集合
        List<SysMenu> menuList = new ArrayList<>();
        // 去重后的集合
        List<SysMenu> menus = new ArrayList<>();
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", username));
        List<SysUserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("user_id", user.getId()));
        userRoleList.stream().parallel().map(
                userRole -> roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", userRole.getRoleId())))
                .forEach(roleMenuList -> roleMenuList.stream().parallel().forEach(roleMenu -> {
            SysMenu menu = menuMapper.selectOne(new QueryWrapper<SysMenu>().eq("id", roleMenu.getMenuId()));
            menuList.add(menu);
        }));
//        menuList = menuMapper.getMenuListByUserName(username);
        menus = menuList.stream().parallel().collect(Collectors.collectingAndThen(
                Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(SysMenu::getMenuId))),
                ArrayList::new));
        for (SysMenu menu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            voList.add(menuToMenuVo(sysMenuVo, menu));
        }
        // 根据父级Id进行分组
        Map<String, List<SysMenuVo>> map = voList.stream().parallel().filter(item -> item.getParentId() != null).collect(Collectors.groupingBy(SysMenu::getParentId));
        // 父级菜单集合
        List<SysMenuVo> collect = voList.stream().parallel().filter(item -> item.getParentId() == null).collect(Collectors.toList());
        if (map.size() <= collect.size()) {
            for (SysMenuVo vo : collect) {
                for (String s : map.keySet()) {
                    if (vo.getMenuId().equals(s)) {
                        List<SysMenuVo> list = map.get(s).stream().parallel().sorted(Comparator.comparing(SysMenu::getMenuSort)).collect(Collectors.toList());
                        vo.setMenuVoList(list);
                    }
                }
                resultList.add(vo);
            }
        }
        return resultList.stream().sorted(Comparator.comparing(SysMenu::getMenuSort)).collect(Collectors.toList());
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
            resultList.add(menuToMenuVo(sysMenuVo, sysMenu));
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
        // 菜单集合
        List<SysMenu> menuList = this.list();
        // 角色菜单集合
        List<SysRoleMenu> roleMenus = roleMenuMapper.selectList(new QueryWrapper<SysRoleMenu>().eq("role_id", roleId));
        return ListToHierarchyListService.listToHierarchyList(menuList,roleMenus);
    }

    /*
     * @Description 删除菜单
     * @param ids
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean delete(List<String> ids) {
        if (ids != null) {
            for (String id : ids) {
                List<SysRoleMenu> list = roleMenuService.list(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
                if (list.size() == 0) {
                    continue;
                } else {
                    roleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("menu_id", id));
                }
            }
            boolean b = this.removeByIds(ids);
            if (b) ;
            return true;
        }
        return false;
    }

    /*
     * @Description menu对象转为menuvo对象
     * @param menuVo
     * @param menu
     **/
    public SysMenuVo menuToMenuVo(SysMenuVo menuVo, SysMenu menu) {
        menuVo.setMenuId(menu.getMenuId());
        menuVo.setParentName(null);
        menuVo.setMenuVoList(null);
        menuVo.setCreateTime(menu.getCreateTime());
        menuVo.setUpdateTime(menu.getUpdateTime());
        menuVo.setMenuUrl(menu.getMenuUrl());
        menuVo.setMenuSort(menu.getMenuSort());
        menuVo.setDescription(menu.getDescription());
        menuVo.setParentId(menu.getParentId());
        menuVo.setMenuName(menu.getMenuName());
        return menuVo;
    }
}
