package com.sym.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sym.system.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {
    /**
     * 根据用户ID查询角色列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<SysRole> selectRolesByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询权限标识列表
     *
     * @param roleId 角色ID
     * @return 权限标识列表
     */
    List<String> selectPermsByRoleId(@Param("roleId") Long roleId);


}
