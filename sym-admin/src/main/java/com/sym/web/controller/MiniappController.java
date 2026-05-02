package com.sym.web.controller;

import com.sym.system.domain.dto.MiniappLoginRequest;
import com.sym.system.domain.vo.MiniappLoginVO;
import com.sym.system.service.IMiniappLoginService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sym.com.common.result.AjaxResult;

@Slf4j
@RestController
@RequestMapping("/auth/wx")
public class MiniappController {

    @Resource
    private IMiniappLoginService miniappLoginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody MiniappLoginRequest request) {
        try {
            MiniappLoginVO vo = miniappLoginService.login(request.getCode());
            return AjaxResult.success(vo);
        } catch (Exception e) {
            log.error("小程序登录失败", e);
            return AjaxResult.error("登录失败: " + e.getMessage());
        }

    }

}
