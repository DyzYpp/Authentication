package com.dyz.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyz.authentication.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface SysRoleMapper
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description 角色mapper接口
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

}
