package com.sym.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 后台管理用户登陆请求参数
 */
@Data
public class LoginRequest {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    /**
     * 验证码
     */
    private String captchaCode;

    /**
     * 验证码唯一标识
     */
    private String captchaKey;
}
