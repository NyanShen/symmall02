package com.sym.framework.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 安全上下文工具类
 * 用于获取当前登录用户信息
 */
@Component
public class SecurityContextUtil {
    /**
     * 获取当前登录用户的ID
     *
     * @return 用户ID，如果未登录则返回null
     */
    public static Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Map) {
            Map<String, Object> principal = (Map<String, Object>) authentication.getPrincipal();
            return (Long) principal.get("userId");
        }
        return null;
    }

    /**
     * 获取当前登录用户的详细信息（principal）
     *
     * @return 用户详情Map，包含userId和username，如果未登录则返回null
     */
    public static Map<String, Object> getCurrentUserDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof Map) {
            return (Map<String, Object>) authentication.getPrincipal();
        }
        return null;
    }
    /**
     * 获取当前登录用户的用户名
     *
     * @return 用户名，如果未登录则返回null
     */
    public static String getCurrentUsername() {
        Map<String, Object> userDetails = getCurrentUserDetails();
        if (userDetails != null) {
            return (String) userDetails.get("username");
        }
        return null;
    }

    /**
     * 判断当前用户是否已登录
     *
     * @return true-已登录，false-未登录
     */
    public static boolean isAuthenticated() {
        return getCurrentUserId() != null;
    }

}
