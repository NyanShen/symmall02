package com.sym.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.SysRole;
import com.sym.system.mapper.SysRoleMapper;
import com.sym.system.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;
    /**
     *  根据用户ID查询角色列表
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<SysRole> selectRolesByUserId(Long userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

    /**
     * 根据用户ID查询权限列表
     * @param userId 用户ID
     * @return 权限字符串列表
     */
    @Override
    public Set<String> selectPermsByUserId(Long userId) {
        List<SysRole> roles = selectRolesByUserId(userId);
        Set<String> perms = new HashSet<>();
        for (SysRole role : roles) {
            List<String> rolePerms = sysRoleMapper.selectPermsByRoleId(role.getRoleId());
            perms.addAll(rolePerms);
        }
        return perms;
    }

    /**
     * 根据用户ID判断用户是否有指定的角色
     * @param userId 用户ID
     * @param roleKey 角色权限字符串
     * @return boolean
     */
    @Override
    public boolean hasRole(Long userId, String roleKey) {
        List<SysRole> roles = sysRoleMapper.selectRolesByUserId(userId);
        return roles.stream()
                .anyMatch(role -> roleKey.equals(role.getRoleKey()));
    }

    /**
     * 根据用户ID判断用户是否有指定的权限
     * @param userId 用户ID
     * @param perm 权限字符串
     * @return boolean
     */
    @Override
    public boolean hasPerm(Long userId, String perm) {
        List<String> perms = sysRoleMapper.selectPermsByRoleId(userId);
        return perms.contains(perm);
    }
}
