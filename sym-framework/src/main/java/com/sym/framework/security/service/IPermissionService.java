package com.sym.framework.security.service;

import java.util.List;
import java.util.Set;

/**
 * 权限加载服务接口
 * 用于在JWT过滤器中加载用户权限信息
 * 具体实现在业务模块中提供(解决模块循环引入问题)
 */
public interface IPermissionService {
    /**
     * 根据用户ID查询角色列表
     * @param userId 用户ID
     * @return 角色Key列表
     */
    List<String> getRoleKeysByUserId(Long userId);

    /**
     * 根据用户ID查询权限标识集合
     * @param userId 用户ID
     * @return 权限标识集合
     */
    Set<String> getPermsByUserId(Long userId);
}
