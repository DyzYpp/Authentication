package com.dyz.authentication.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.VO.PageVo;
import com.dyz.authentication.entity.VO.SysRoleAndPermissionVo;
import com.dyz.authentication.entity.VO.SysUserVo;
import com.dyz.authentication.util.returnResult.AjaxResult;

import java.util.List;

/**
 * @interface UserService
 * @Author Duan yuzhe
 * @Date 2020/9/1
 * @description
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 检查账号信息是否正确
     */
    boolean checkLoginInfo(String userName,String passWord) throws Exception;

    /**
     * 通过用户名查询用户信息
     */
    SysUser getUserByUserName(String userName);

    /**
     * 注册用户/添加用户
     */
    AjaxResult registerUser(SysUserVo sysUserVO);

    /**
     * 修改用户
     */
    boolean updateUser(SysUserVo sysUserVO);

    /**
     * 分页查询用户
     */
    List<SysUser> getUserByPage(PageVo pageVo);

    /**
     * 查询用户列表中对应角色id包含roleId的用户
     */
    List<SysRoleAndPermissionVo> getUserListByRoleId(String roleId);

    /**
     * 删除api接口
     */
    boolean delete(List<String> ids);

}
