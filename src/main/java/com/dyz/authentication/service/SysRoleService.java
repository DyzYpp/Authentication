package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysRole;
import com.dyz.authentication.entity.VO.PageVo;

import java.util.List;

/**
 * @interface SysRoleService
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description 角色Service层接口
 */
public interface SysRoleService extends IService<SysRole> {
    /**
     * 根据用户名查询用户角色
     */
    List<SysRole> getRoleListByUserName(String username);

    /**
     * 分页查询角色列别
     */
    List<SysRole> getRoleByPage(PageVo pageVo);

    /**
     * 删除api接口
     */
    boolean delete(List<String> ids);
}
