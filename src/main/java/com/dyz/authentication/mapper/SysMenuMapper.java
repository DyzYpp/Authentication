package com.dyz.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyz.authentication.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface SysMenuMapper
 * @Author Duan yuzhe
 * @Date 2020/9/2
 * @description 菜单Mapper层接口
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {


    /**
     * 根据用户名获取菜单
     * 控制权限
     */
    List<SysMenu> getMenuListByUserName(@Param("username") String username);

}
