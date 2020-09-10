package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @ClassName User
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description 系统用户实体类
 */
@TableName(value = "sys_user")
public class SysUser{

    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    private String userName;

    private String password;

    private String description;

    private Integer status;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    public SysUser() {
    }

    public SysUser(String id, String userName, String password, String description, Integer status) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.description = description;
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SysUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
