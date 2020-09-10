package com.dyz.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyz.authentication.entity.SysApiInterface;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @interface SysApiInterfaceMapper
 * @Author Duan yuzhe
 * @Date 2020/9/3
 * @description api接口mapper接口
 */
@Mapper
public interface SysApiInterfaceMapper extends BaseMapper<SysApiInterface> {

    /**
     * 通过用户名获取Api列表
     */
    List<SysApiInterface> getApiInterfaceListByUserName(@Param("username") String username);

}
