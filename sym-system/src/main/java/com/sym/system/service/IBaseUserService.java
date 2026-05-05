package com.sym.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sym.system.domain.BaseUser;
import com.sym.system.domain.dto.LoginRequest;
import com.sym.system.domain.vo.LoginVO;

public interface IBaseUserService extends IService<BaseUser> {
    /**
     * 后台用户登录
     * @param request 登录请求参数
     * @return 登录结果
     */
    LoginVO login(LoginRequest request);

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户信息
     */
    BaseUser findByUsername(String username);

}
