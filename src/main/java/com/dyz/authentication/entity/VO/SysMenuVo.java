package com.dyz.authentication.entity.VO;

import com.dyz.authentication.entity.SysMenu;

/**
 * @ClassName SysMenuVo
 * @Author Duan yuzhe
 * @Date 2020/9/8
 */
public class SysMenuVo extends SysMenu {

    private String parentName;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }
}
