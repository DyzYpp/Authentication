package com.dyz.authentication.entity.Vo;

import com.dyz.authentication.entity.SysMenu;

import java.util.List;

/**
 * @ClassName SysMenuVo
 * @Author Duan yuzhe
 * @Date 2020/9/8
 */
public class SysMenuVo extends SysMenu {

    private String parentName;

    private List<SysMenuVo> menuVoList;

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public List<SysMenuVo> getMenuVoList() {
        return menuVoList;
    }

    public void setMenuVoList(List<SysMenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }

    @Override
    public String toString() {
        return "SysMenuVo{" +
                "parentName='" + parentName + '\'' +
                ", menuVoList=" + menuVoList +
                '}';
    }
}
