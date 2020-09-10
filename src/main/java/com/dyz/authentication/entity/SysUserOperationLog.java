package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;

/**
 * @ClassName SysUserOperationLog
 * @Author Duan yuzhe
 * @Date 2020/9/10
 * @description
 */
@TableName("sys_user_operation_log")
public class SysUserOperationLog {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String operationUser;

    @TableField(fill = FieldFill.INSERT)
    private Date operationTime;

    private String requestUri;

    private String requestMethod;

    private String requestDescription;

    private String succeed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationUser() {
        return operationUser;
    }

    public void setOperationUser(String operationUser) {
        this.operationUser = operationUser;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getRequestDescription() {
        return requestDescription;
    }

    public void setRequestDescription(String requestDescription) {
        this.requestDescription = requestDescription;
    }

    public String getSucceed() {
        return succeed;
    }

    public void setSucceed(String succeed) {
        this.succeed = succeed;
    }

    @Override
    public String toString() {
        return "SysUserOperationLog{" +
                "id='" + id + '\'' +
                ", operationUser='" + operationUser + '\'' +
                ", operationTime=" + operationTime +
                ", requestUri='" + requestUri + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", requestDescription='" + requestDescription + '\'' +
                ", succeed='" + succeed + '\'' +
                '}';
    }
}
