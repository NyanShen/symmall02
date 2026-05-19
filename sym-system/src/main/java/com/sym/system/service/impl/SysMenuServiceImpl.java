package com.sym.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.SysMenu;
import com.sym.system.domain.vo.RouteMenuVO;
import com.sym.system.mapper.SysMenuMapper;
import com.sym.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Override
    public List<SysMenu> selectMenusByUserId(Long userId) {
        return sysMenuMapper.selectMenusByUserId(userId);
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        List<SysMenu> tree = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(0L)) {
                tree.add(findChildren(menu, menus));
            }
        }
        return tree;
    }

    /**
     * 递归查找子节点
     *
     * @param parent 父级节点
     * @param menus 所有节点
     * @return 树形结构
     */
    private SysMenu findChildren(SysMenu parent, List<SysMenu> menus) {
        List<SysMenu> children = menus.stream()
                .filter(menu -> menu.getParentId().equals(parent.getMenuId()))
                .map(menu -> findChildren(menu, menus))
                .collect(Collectors.toList());

        parent.setChildren(children);
        return parent;
    }

//    /**
//     * 获取用户菜单路由
//     * @param userId 用户ID
//     * @return 菜单及路由
//     */
    @Override
    public List<RouteMenuVO> buildRouteMenuTreeByUserId(Long userId) {
        List<SysMenu> menus = selectMenusByUserId(userId);
        List<SysMenu> menuTree = buildMenuTree(menus);
        return convertToRouteMenuVO(menuTree);
    }

    /**
     *
     */
    private List<RouteMenuVO> convertToRouteMenuVO(List<SysMenu> menuTree) {
        return menuTree.stream()
                .map(this::convertSingleMenu)
                .collect(Collectors.toList());
    }

    private RouteMenuVO convertSingleMenu(SysMenu menu) {
        RouteMenuVO routeMenu = new RouteMenuVO();
        routeMenu.setId(menu.getMenuId());
        routeMenu.setName(StringUtils.capitalize(menu.getPath()));
        routeMenu.setPath(menu.getPath());
        routeMenu.setComponent(menu.getComponent());

        // 菜单类型为目录 才需要设置子菜单
        if ("M".equals(menu.getMenuType()) && menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            routeMenu.setRedirect("noRedirect");
        }

        RouteMenuVO.Meta meta = new RouteMenuVO.Meta();
        meta.setTitle(menu.getMenuName());
        meta.setIcon(menu.getIcon());
        meta.setVisible("0".equals(menu.getVisible()));
        meta.setKeepAlive("0".equals(menu.getIsCache()));
        routeMenu.setMeta(meta);

        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            routeMenu.setChildren(convertToRouteMenuVO(menu.getChildren()));
        }

        return routeMenu;
    }
}
