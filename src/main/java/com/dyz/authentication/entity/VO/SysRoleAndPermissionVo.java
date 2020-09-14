package com.dyz.authentication.entity.Vo;

import java.util.List;

/**
 * @ClassName SysRoleAndPermissionVo
 * @Author Duan yuzhe
 * @Date 2020/9/8
 * @description
 */
public class SysRoleAndPermissionVo {

    private String id;
    private String name;
    private String roleId;
    private String pid;
    private List<SysRoleAndPermissionVo> childrenList;

    public SysRoleAndPermissionVo() {
    }

    public SysRoleAndPermissionVo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public SysRoleAndPermissionVo(String id, String name, String pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }


    public SysRoleAndPermissionVo(String id, String name, String roleId, String pid) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
        this.pid = pid;
    }

    public SysRoleAndPermissionVo(String id, String name, String roleId, String pid, List<SysRoleAndPermissionVo> childrenList) {
        this.id = id;
        this.name = name;
        this.roleId = roleId;
        this.pid = pid;
        this.childrenList = childrenList;
    }

    public List<SysRoleAndPermissionVo> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<SysRoleAndPermissionVo> childrenList) {
        this.childrenList = childrenList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        return "SysRoleAndPermissionVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", roleId='" + roleId + '\'' +
                ", pid='" + pid + '\'' +
                ", childrenList=" + childrenList +
                '}';
    }
}
