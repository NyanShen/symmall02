package com.sym.framework.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 安全上下文工具类
 * 用于获取当前登录用户信息(用户ID，用户名称，角色，权限)
 * 角色（是否有某一角色）
 * 权限（用户权限列表，用户是否有权限）
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

    /**
     * 获取当前用户权限
     */
    public static Collection<String> getCurrentUserPermissions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());
        }
        return null;
    }
    /**
     * 检查当前用户是否拥有指定角色
     */
    public static boolean hasRole(String roleKey) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> ("ROLE_"+ roleKey).equals(authority.getAuthority()));
        }
        return false;
    }

    /**
     * 检查当前用户是否拥有指定权限
     *
     * @param permission 权限标识
     * @return true-拥有 false-不拥有
     */
    public static boolean hasPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getAuthorities() != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(authority -> permission.equals(authority.getAuthority()));
        }
        return false;
    }

    /**
     * 判断当前用户是否为管理员
     *
     * @return true-是管理员 false-不是管理员
     */
    public static boolean isAdmin() {
        return hasRole("admin");
    }

    /**
     * 判断当前用户是否为小程序用户
     *
     * @return true-是小程序用户 false-不是小程序用户
     */
    public static boolean isMiniappUser() {
        return hasRole("miniapp_user");
    }

}
