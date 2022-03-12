package com.dyz.authentication.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @ClassName SysUserVO
 * @Author
 * @Date 2020/9/4
 * @description
 */
@Data
public class SysUserVo {

    private String id;

    private String userName;

    private String passWord;

    private String idCard;

    private Integer sex;

    private Integer age;

}
