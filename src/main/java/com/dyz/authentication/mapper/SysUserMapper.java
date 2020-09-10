package com.dyz.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyz.authentication.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @interface UserMapper
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description 用户Mapper层接口
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 通过用户名获取用户信息
     */
    SysUser getUserByUserName(@Param("username") String userName);
}
