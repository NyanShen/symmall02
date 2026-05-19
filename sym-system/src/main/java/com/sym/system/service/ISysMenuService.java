package com.sym.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sym.system.domain.SysMenu;
import com.sym.system.domain.vo.RouteMenuVO;

import java.util.List;

public interface ISysMenuService extends IService<SysMenu> {
    /**
     * 根据用户ID查询菜单列表
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    List<SysMenu> selectMenusByUserId(Long userId);

    /**
     * 构建前端路由树
     *
     * @param menus 菜单列表
     * @return 树形结构菜单
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 根据用户ID构建前端路由菜单树
     *
     * @param userId 用户ID
     * @return 前端路由菜单树
     */
    List<RouteMenuVO> buildRouteMenuTreeByUserId(Long userId);

}
