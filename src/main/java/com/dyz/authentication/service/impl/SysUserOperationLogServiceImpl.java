package com.dyz.authentication.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dyz.authentication.entity.SysUserOperationLog;
import com.dyz.authentication.mapper.SysUserOperationLogMapper;
import com.dyz.authentication.service.SysUserOperationLogService;
import org.springframework.stereotype.Service;

/**
 * @ClassName SysUserOperationLogServiceImpl
 * @Author
 * @Date 2020/9/10
 * @description
 */
@Service("userOperationService")
public class SysUserOperationLogServiceImpl extends ServiceImpl<SysUserOperationLogMapper, SysUserOperationLog> implements SysUserOperationLogService {
}
