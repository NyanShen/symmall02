package com.sym.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.BaseUser;
import com.sym.system.mapper.BaseUserMapper;
import com.sym.system.service.IBaseUserService;
import org.springframework.stereotype.Service;

@Service
public class BaseUserServiceImpl extends ServiceImpl<BaseUserMapper, BaseUser> implements IBaseUserService {
}
