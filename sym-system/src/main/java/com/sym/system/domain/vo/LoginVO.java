package com.sym.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 登录token
     */
    private String token;
    /**
     * 登录用户信息
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

}
