package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysUser;
import com.dyz.authentication.entity.SysUserRole;
import com.dyz.authentication.entity.Vo.PageVo;
import com.dyz.authentication.entity.Vo.SysRoleAndPermissionVo;
import com.dyz.authentication.entity.Vo.SysUserVo;
import com.dyz.authentication.mapper.SysUserMapper;
import com.dyz.authentication.mapper.SysUserRoleMapper;
import com.dyz.authentication.service.SysUserRoleService;
import com.dyz.authentication.service.SysUserService;
import com.dyz.authentication.util.BCryptPasswordEncoderUtil;
import com.dyz.authentication.util.ResultUtil.AjaxResult;
import com.dyz.authentication.util.ResultUtil.Enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Author Daan yuzhe
 * @Date 2020/9/1
 * @description 用户接口方法实现类(service层)
 */
@Service("userService")
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    BCryptPasswordEncoderUtil bCryptPasswordEncoderUtil;

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    SysUserRoleMapper userRoleMapper;

    @Autowired
    SysUserRoleService userRoleService;

    /*
     * @Description 验证用户名密码
     * @param userName
     * @param passWord
     * @return boolean
     **/
    @Override
    public boolean checkLoginInfo(String userName, String passWord) throws Exception {
        SysUser user = this.getUserByUserName(userName);
        if (user == null){
            throw new Exception("账号不存在,请重新尝试");
        } else {
            String encodedPassword = user.getPassWord();
            if (!bCryptPasswordEncoderUtil.matches(passWord,encodedPassword)){
                throw new Exception("密码不正确！");
            } else {
                return true;
            }
        }
    }

    /*
     * @Description 根据用户名查询用户信息
     * @param userName
     * @return com.dyz.authentication.entity.SysUser
     **/
    @Override
    public SysUser getUserByUserName(String username) {
        return userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name",username));
    }

    /*
     * @Description 用户注册
     * @param sysUserVO
     * @return com.dyz.authentication.util.returnResult.AjaxResult
     **/
    @Override
    public AjaxResult registerUser(SysUserVo sysUserVO) {
        SysUser user = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_name", sysUserVO.getUserName()));
        if (user != null){
            return AjaxResult.error(Enums.USER_REGISTER.getMsg(), Enums.USER_REGISTER.getCode());
        } else {
            String passWord = bCryptPasswordEncoderUtil.encode(sysUserVO.getPassWord());
            SysUser sysUser = new SysUser(sysUserVO.getId(),sysUserVO.getUserName(),passWord,sysUserVO.getDescription(),1);
            return this.save(sysUser) == true ? AjaxResult.success("操作成功") : AjaxResult.error("操作失败");
        }
    }

    /*
     * @Description 修改 用户
     * @param sysUserVO
     * @return boolean
     **/
    @Override
    public boolean updateUser(SysUserVo sysUserVO) {
        SysUser user = this.getById(sysUserVO.getId());
        if (user != null){
            user.setUserName(sysUserVO.getUserName());
            user.setPassWord(bCryptPasswordEncoderUtil.encode(sysUserVO.getPassWord()));
            user.setDescription(sysUserVO.getDescription());
        }
        boolean b = this.updateById(user);
        return b;
    }

    /*
     * @Description 分页查询用户列表
     * @param pageVo
     * @return java.util.List<com.dyz.authentication.entity.SysUser>
     **/
    @Override
    public List<SysUser> getUserByPage(PageVo pageVo) {
        IPage<SysUser> res;
        List<SysUser> users;
        if (pageVo.getCondition().equals("") || pageVo.getType().equals("")){
            res = userMapper.selectPage(new Page<SysUser>(pageVo.getPage(),pageVo.getPageSize()),
                    new QueryWrapper<SysUser>().orderByAsc("user_name"));
        } else {
            res = userMapper.selectPage(new Page<SysUser>(pageVo.getPage(), pageVo.getPageSize()),
                    new QueryWrapper<SysUser>().like(pageVo.getType(), pageVo.getCondition()).orderByAsc("user_name"));
        }
        users = res.getRecords();
        return users;
    }

    /*
     * @Description 查询用户列表中对应角色id包含roleId的所有用户
     * @param roleId
     * @return java.util.List<com.dyz.authentication.entity.VO.SysRoleAndPermissionVo>
     **/
    @Override
    public List<SysRoleAndPermissionVo> getUserListByRoleId(String roleId) {
        // 存储结果的集合
        List<SysRoleAndPermissionVo> resultList = new ArrayList<>();
        // 存储用户转换为SysRoleAndPermissionVo集合
        List<SysRoleAndPermissionVo> permissionVoList = new ArrayList<>();
        // 用户列表
        List<SysUser> userList = this.list();
        // 拥有当前角色的用户角色中间列表
        List<SysUserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<SysUserRole>().eq("role_id", roleId));
        for (SysUser user : userList) {
            SysRoleAndPermissionVo permissionVo = new SysRoleAndPermissionVo(user.getId(),user.getUserName());
            permissionVoList.add(permissionVo);
        }
        // 给拥有当前角色的用户赋值roleId
        for (SysRoleAndPermissionVo vo : permissionVoList) {
            for (SysUserRole userRole : userRoleList) {
                if (vo.getId().equals(userRole.getUserId())){
                    vo.setRoleId(userRole.getRoleId());
                }
            }
        }
        return permissionVoList;
    }

    /*
     * @Description 删除用户
     * @param ids
     * @return boolean
     **/
    @Override
    @Transactional
    public boolean delete(List<String> ids) {
        if (ids != null){
            for (String id : ids) {
                List<SysUserRole> list = userRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id", id));
                if (list.size() != 0){
                    userRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", id));
                }
            }
            boolean b = this.removeByIds(ids);
            if (b); return true;
        }
        return false;
    }

    /*
     * @Description 重置密码
     * @param sysUserVo
     * @return boolean
     **/
    @Override
    public boolean resetPassword(SysUserVo sysUserVo) {
        SysUser user = this.getById(sysUserVo.getId());
        user.setPassWord(bCryptPasswordEncoderUtil.encode("000000"));
        boolean update = this.updateById(user);
        return update;
    }
}
