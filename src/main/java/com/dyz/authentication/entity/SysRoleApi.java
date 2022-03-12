package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @ClassName SysRoleApi
 * @Author
 * @Date 2020/9/9
 * @description
 */
@TableName(value = "sys_role_api")
@Entity
public class SysRoleApi {

    @Id
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String roleId;

    private String apiId;

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

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    @Override
    public String toString() {
        return "SysRoleApi{" +
                "id='" + id + '\'' +
                ", roleId='" + roleId + '\'' +
                ", apiId='" + apiId + '\'' +
                '}';
    }
}
