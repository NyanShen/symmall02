package com.sym.system.service;

import com.sym.system.domain.vo.MiniappLoginVO;

public interface IMiniappLoginService {
    /**
     * 小程序登录
     * @param code
     * @return 登录信息
     */
    MiniappLoginVO login(String code);
}
