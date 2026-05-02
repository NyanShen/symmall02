package com.sym.system.domain.dto;

import lombok.Data;

/**
 * 小程序静默登录的流程是：
 * 小程序端调用 wx.login() 获取 code
 * 将 code 发送到后端
 * 后端用 code 换取 openid 和 session_key
 * 根据 openid 查询或创建用户
 * 生成 JWT token 返回给前端
 */
@Data
public class MiniappLoginRequest {
    private String code;
}
