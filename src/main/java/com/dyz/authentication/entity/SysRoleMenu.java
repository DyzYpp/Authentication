package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * @ClassName SysRoleMenu
 * @Author Duan yuzhe
 * @Date 2020/9/8
 * @description
 */
@TableName(value = "sys_role_menu")
public class SysRoleMenu {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String menuId;

    private String roleId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "SysRoleMenu{" +
                "id='" + id + '\'' +
                ", menuId='" + menuId + '\'' +
                ", roleId='" + roleId + '\'' +
                '}';
    }
}
