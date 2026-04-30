package com.sym.framework.security.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 密码工具类
 */
public class PasswordUtil {
    // 密码编码器
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 密码加密
     */
    public static String encode(String password) {
        return passwordEncoder.encode(password);
    }
    /**
     * 密码匹配
     */
    public static boolean matches(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
