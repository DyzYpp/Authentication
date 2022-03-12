package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysApiInterface
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description api接口实体类
 */
@TableName(value = "sys_api_interface")
@Entity
public class SysApiInterface {

    @Id
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String apiId;

    private String apiName;

    private String apiUrl;

    private String pid;

    private String apiMethod;

    private Integer apiSort;

    private String description;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    public SysApiInterface() {
    }

    public SysApiInterface(String apiId, String apiName, String apiUrl, String apiMethod, Integer apiSort, String description) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.apiMethod = apiMethod;
        this.apiSort = apiSort;
        this.description = description;
    }

    public SysApiInterface(String apiId, String apiName, String apiUrl, String pid, String apiMethod, Integer apiSort, String description, Date createTime, Date updateTime) {
        this.apiId = apiId;
        this.apiName = apiName;
        this.apiUrl = apiUrl;
        this.pid = pid;
        this.apiMethod = apiMethod;
        this.apiSort = apiSort;
        this.description = description;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public Integer getIsDelete() {
        return isDeleted;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDeleted = isDeleted;
    }

    public String getApiId() {
        return apiId;
    }

    public void setApiId(String apiId) {
        this.apiId = apiId;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiMethod() {
        return apiMethod;
    }

    public void setApiMethod(String apiMethod) {
        this.apiMethod = apiMethod;
    }

    public Integer getApiSort() {
        return apiSort;
    }

    public void setApiSort(Integer apiSort) {
        this.apiSort = apiSort;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public String toString() {
        return "SysApiInterface{" +
                "apiId='" + apiId + '\'' +
                ", apiName='" + apiName + '\'' +
                ", apiUrl='" + apiUrl + '\'' +
                ", pid='" + pid + '\'' +
                ", apiMethod='" + apiMethod + '\'' +
                ", apiSort=" + apiSort +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
