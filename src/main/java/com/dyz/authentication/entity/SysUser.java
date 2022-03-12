package com.dyz.authentication.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dyz.authentication.entity.VO.SysUserVo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName User
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description 系统用户实体类
 */
@TableName(value = "sys_user")
@Entity
@Data
public class SysUser{

    @Id
    @TableId(value = "id",type = IdType.ASSIGN_UUID)
    private String id;

    @TableField("user_name")
    private String userName;

    @TableField("pass_word")
    private String passWord;

    @TableField("id_card")
    private String idCard;

    @TableField("sex")
    private Integer sex;

    @TableField("age")
    private Integer age;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    private Integer isDeleted;

    public SysUser() {
    }

    public SysUser(SysUserVo userVo) {
        this.id = userVo.getId();
        this.userName = userVo.getUserName();
        this.passWord = userVo.getPassWord();
        this.age = userVo.getAge();
        this.idCard = userVo.getIdCard();
        this.sex = userVo.getSex();
    }

}
