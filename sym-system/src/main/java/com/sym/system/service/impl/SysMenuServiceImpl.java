package com.sym.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sym.system.domain.SysMenu;
import com.sym.system.mapper.SysMenuMapper;
import com.sym.system.service.ISysMenuService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

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
        return List.of();
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
}
