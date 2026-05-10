package com.sym.system.service.impl;

import com.sym.framework.security.service.IPermissionService;
import com.sym.system.domain.SysRole;
import com.sym.system.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PermissionServiceImpl implements IPermissionService {

    @Resource
    private ISysRoleService sysRoleService;
    @Override
    public List<String> getRoleKeysByUserId(Long userId) {
        List<SysRole> roles = sysRoleService.selectRolesByUserId(userId);
        return roles.stream().map(SysRole::getRoleKey).toList();
    }

    @Override
    public Set<String> getPermsByUserId(Long userId) {
        return sysRoleService.selectPermsByUserId(userId);
    }
}
