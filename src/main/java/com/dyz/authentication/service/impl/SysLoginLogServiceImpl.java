package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysLoginLog;
import com.dyz.authentication.mapper.SysLoginLogMapper;
import com.dyz.authentication.service.SysLoginLogService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysLoginLogServiceImpl
 * @Author
 * @Date 2020/9/10
 * @description
 */
@Service("loginLogService")
public class SysLoginLogServiceImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements SysLoginLogService {
}
