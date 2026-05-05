package com.sym.web.controller;

import com.sym.common.result.AjaxResult;
import com.sym.framework.security.utils.SecurityContextUtil;
import com.sym.system.domain.BaseUser;
import com.sym.system.service.IBaseUserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IBaseUserService baseUserService;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/info")
    public AjaxResult getUserInfo() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        BaseUser user = baseUserService.getById(userId);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        return AjaxResult.success(user).put("userId", userId);
    }
}
