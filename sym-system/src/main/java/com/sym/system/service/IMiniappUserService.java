package com.sym.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sym.system.domain.MiniappUser;

public interface IMiniappUserService extends IService<MiniappUser> {

    MiniappUser getByOpenid(String openId);

    MiniappUser getByUserId(String userId);
}
