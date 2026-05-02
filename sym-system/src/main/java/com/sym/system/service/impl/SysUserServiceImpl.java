package com.sym.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.SysUser;
import com.sym.system.mapper.SysUserMapper;
import com.sym.system.service.ISysUserService;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
}
