package com.dyz.authentication.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dyz.authentication.entity.SysLoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * @interface SysLoginLogMapper
 * @Author Duan yuzhe
 * @Date 2020/9/10
 * @description
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog> {
}
