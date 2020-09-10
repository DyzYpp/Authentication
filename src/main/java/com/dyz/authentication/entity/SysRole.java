package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @ClassName SysRole
 * @Author
 * @Date 2020/9/3
 * @description
 */
@TableName(value = "sys_role")
public class SysRole {

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String roleName;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public SysRole() {
    }

    public SysRole(String id, String roleName, String description) {
        this.id = id;
        this.tranRoleName(roleName);
        this.description = description;
    }

    public SysRole(String id, String roleName) {
        this.id = id;
        this.tranRoleName(roleName);
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private void tranRoleName(String roleName) {
        if (roleName.indexOf("ROLE_") == -1) {
            this.roleName = "ROLE_" + roleName;
        } else {
            this.roleName = roleName;
        }
    }


}
