package com.sym.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sym.system.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @Param 让 MyBatis 能够准确识别和绑定方法参数到 SQL 语句中的占位符,提高代码可读性和可维护性。
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(@Param("userId") Long userId);

    /**
     * 根据角色ID查询菜单列表
     *
     * @param roleId 角色ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByRoleId(@Param("roleId") Long roleId);

}
