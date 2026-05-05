package com.sym.web.controller;

import com.sym.common.result.AjaxResult;
import com.sym.system.domain.dto.LoginRequest;
import com.sym.system.domain.dto.MiniappLoginRequest;
import com.sym.system.domain.vo.LoginVO;
import com.sym.system.domain.vo.MiniappLoginVO;
import com.sym.system.service.IBaseUserService;
import com.sym.system.service.IMiniappLoginService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Resource
    private IBaseUserService baseUserService;

    @Resource
    private IMiniappLoginService miniappLoginService;

    @PostMapping("/login")
    public AjaxResult login(@Valid @RequestBody LoginRequest request) {
        LoginVO loginVO = baseUserService.login(request);
        return AjaxResult.success(loginVO);
    }
    /**
     * 小程序静默登录
     * @param request code
     * @return token
     */
    @PostMapping("/wx/login")
    public AjaxResult login(@Valid @RequestBody MiniappLoginRequest request) {
        try {
            MiniappLoginVO vo = miniappLoginService.login(request.getCode());
            return AjaxResult.success(vo);
        } catch (Exception e) {
            log.error("小程序登录失败", e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        }

    }
}
