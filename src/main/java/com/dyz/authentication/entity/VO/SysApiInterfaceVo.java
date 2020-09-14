package com.dyz.authentication.entity.Vo;

import com.dyz.authentication.entity.SysApiInterface;

/**
 * @ClassName SysApiInterfaceVo
 * @Author Duan yuzhe
 * @Date 2020/9/8
 */
public class SysApiInterfaceVo extends SysApiInterface {

    String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
