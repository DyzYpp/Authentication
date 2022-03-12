package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysRole
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description
 */
@TableName(value = "sys_role")
@Entity
public class SysRole {

    @Id
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String roleName;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    public SysRole() {
    }

    public SysRole(String id, String roleName, String description) {
        this.id = id;
        this.tranRoleName(roleName);
        this.description = description;
    }

    public SysRole(String roleName) {
        this.tranRoleName(roleName);
    }

    @Override
    public String toString() {
        return "SysRole{" +
                "id='" + id + '\'' +
                ", roleName='" + roleName + '\'' +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
        this.tranRoleName(roleName);
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.tranRoleName(roleName);
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
