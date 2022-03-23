package com.dyz.authentication.service.impl;

import com.dyz.authentication.entity.SysApiInterface;
import com.dyz.authentication.entity.SysMenu;
import com.dyz.authentication.entity.SysRoleApi;
import com.dyz.authentication.entity.SysRoleMenu;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ListToHierarchyListService
 * @Author 将查询列表转为具有层级关系列表
 * @Date 2020/9/12
 * @description
 */
public class ListToHierarchyListService {

    /*
     * @Description 转换为具有层级关系
     * @param list
     * @param middleList
     **/
    public static  List<SysRoleAndPermissionVo>  listToHierarchyList(List<?> list,List<?> middleList){
        // 存储list转换为SysRoleAndPermissionVo集合
        List<SysRoleAndPermissionVo> permissionVoList = new ArrayList<>();
        // 将list转换为泛型为SysMenu对象集合
        List<SysMenu> menuList = null;
        // 将middleList转换为泛型为SysRoleMenu的对象集合
        List<SysRoleMenu> roleMenus = null;
        // 将list转换为泛型为api对象集合
        List<SysApiInterface> apiInterfaceList = null;
        // 将middleList转换为泛型为SysRoleApi的对象集合
        List<SysRoleApi> roleApiList = null;
        if (list.size() != 0 && middleList.size() != 0){
            if (list.get(0).getClass().getName().contains("SysMenu")){
                menuList = (List<SysMenu>)list;
                roleMenus = (List<SysRoleMenu>)middleList;
                for (SysMenu menu : menuList) {
                    SysRoleAndPermissionVo permissionVo = new SysRoleAndPermissionVo(menu.getMenuId(), menu.getMenuName(), menu.getParentId());
                    permissionVoList.add(permissionVo);
                }
            }
            if (list.get(0).getClass().getName().indexOf("SysApiInterface") != -1){
                apiInterfaceList = (List<SysApiInterface>)list;
                roleApiList = (List<SysRoleApi>)middleList;
                for (SysApiInterface apiInterface : apiInterfaceList) {
                    String name = apiInterface.getApiUrl().equals("NONE") ?
                            apiInterface.getApiName() : apiInterface.getApiName()
                            + "（uri：" + apiInterface.getApiUrl() + "，请求方式：" + apiInterface.getApiMethod() + "）";
                    SysRoleAndPermissionVo permissionVo = new SysRoleAndPermissionVo(apiInterface.getApiId(),name,apiInterface.getPid());
                    permissionVoList.add(permissionVo);
                }
            }
        }
        // 给当前角色权限范围内的菜单对象赋值roleId
        for (SysRoleAndPermissionVo permissionVo : permissionVoList) {
            if (roleMenus != null && menuList != null){
                for (SysRoleMenu roleMenu : roleMenus) {
                    if (permissionVo.getId().equals(roleMenu.getMenuId())) {
                        permissionVo.setRoleId(roleMenu.getRoleId());
                    }
                }
            }
            if (apiInterfaceList != null && roleApiList != null){
                for (SysRoleApi roleApi : roleApiList) {
                    if (permissionVo.getId().equals(roleApi.getApiId())){
                        permissionVo.setRoleId(roleApi.getRoleId());
                    }
                }
            }
        }
        return finalList(permissionVoList);
    }

    /*
     * @Description 生成最终列表
     * @param collect
     * @param map
     **/
    public static List<SysRoleAndPermissionVo>  finalList(List<SysRoleAndPermissionVo> permissionVoList){
        // 存储结果的集合
        List<SysRoleAndPermissionVo> resultList = new ArrayList<>();
        // 父级菜单集合
        List<SysRoleAndPermissionVo> collect = permissionVoList.stream().filter(item -> item.getPid() == null).collect(Collectors.toList());
        // 根据parentId分类
        Map<String, List<SysRoleAndPermissionVo>> map = permissionVoList.stream().filter(item -> item.getPid() != null).collect(Collectors.groupingBy(SysRoleAndPermissionVo::getPid));
        if (collect.size() >= map.size()) {
            for (SysRoleAndPermissionVo vo : collect) {
                for (String s : map.keySet()) {
                    if (vo.getId().equals(s)) {
                        vo.setChildrenList(map.get(s));
                    }
                }
                resultList.add(vo);
            }
        }
        return resultList;
    }
}
