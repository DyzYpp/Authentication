package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysLoginLog
 * @Author Duan yuzhe
 * @Date 2020/9/10
 * @description
 */
@TableName("sys_login_log")
@Entity
public class SysLoginLog {

    @Id
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String loginUser;

    @TableField(fill = FieldFill.INSERT)
    private Date loginTime;

    private String ipAddress;

    private String succeed;

    @TableField(fill = FieldFill.INSERT)
    private String loginDescription;

    private String message;

    public SysLoginLog() {
    }

    public SysLoginLog(String loginUser, String ipAddress, String succeed) {
        this.loginUser = loginUser;
        this.ipAddress = ipAddress;
        this.succeed = succeed;
    }

    public SysLoginLog(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    public String getLoginDescription() {
        return loginDescription;
    }

    public void setLoginDescription(String loginDescription) {
        this.loginDescription = loginDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SysLoginLog{" +
                "id='" + id + '\'' +
                ", loginUser='" + loginUser + '\'' +
                ", loginTime=" + loginTime +
                ", ipAddress='" + ipAddress + '\'' +
                ", succeed='" + succeed + '\'' +
                ", loginDescription='" + loginDescription + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
