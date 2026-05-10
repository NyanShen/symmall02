package com.sym.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sym.system.domain.SysRole;

import java.util.List;
import java.util.Set;

public interface ISysRoleService extends IService<SysRole> {
    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(Long userId);

    /**
     * 根据用户ID查询权限标识集合
     *
     * @param userId 用户ID
     * @return 权限标识集合
     */
    Set<String> selectPermsByUserId(Long userId);

    /**
     * 判断用户是否拥有指定角色
     *
     * @param userId 用户ID
     * @param roleKey 角色权限字符串
     * @return true-拥有 false-不拥有
     */
    boolean hasRole(Long userId, String roleKey);

    /**
     * 判断用户是否拥有指定权限
     *
     * @param userId 用户ID
     * @param perm 权限标识
     * @return true-拥有 false-不拥有
     */
    boolean hasPerm(Long userId, String perm);
}
