package com.sym.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.MiniappUser;
import com.sym.system.mapper.MiniappUserMapper;
import com.sym.system.service.IMiniappUserService;
import org.springframework.stereotype.Service;

@Service
public class MiniappUserServiceImpl extends ServiceImpl<MiniappUserMapper, MiniappUser> implements IMiniappUserService
{
    @Override
    public MiniappUser getByOpenid(String openid) {
        LambdaQueryWrapper<MiniappUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MiniappUser::getOpenid, openid);
       return this.getOne(wrapper);
    }

    @Override
    public MiniappUser getByUserId(String userId) {
        LambdaQueryWrapper<MiniappUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MiniappUser::getUserId, userId);
        return this.getOne(wrapper);
    }
}
