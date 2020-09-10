package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysApiInterface;
import com.dyz.authentication.entity.VO.SysApiInterfaceVo;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;

import java.util.List;

/**
 * @interface SysApiInterfaceService
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description api接口service层
 */
public interface SysApiInterfaceService extends IService<SysApiInterface> {


    /**
     *  根据用户名查询api接口权限
     */
    List<SysApiInterface> getApiInterfaceListByUserName(String username);


    /**
     *  查询api列表
     */
    List<SysApiInterfaceVo>  getAllList();

    /**
     *  查询API列表中对应角色id包含roleId的API
     */
    List<SysRoleAndPermissionVo> getApiListByRoleId(String roleId);

    /**
     * 删除api接口
     */
    boolean delete(List<String> ids);
}
