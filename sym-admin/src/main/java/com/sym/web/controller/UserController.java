package com.sym.web.controller;

import com.sym.common.result.AjaxResult;
import com.sym.framework.security.utils.SecurityContextUtil;
import com.sym.system.domain.BaseUser;
import com.sym.system.domain.SysRole;
import com.sym.system.domain.vo.RouteMenuVO;
import com.sym.system.domain.vo.UserInfoVO;
import com.sym.system.service.IBaseUserService;
import com.sym.system.service.ISysMenuService;
import com.sym.system.service.ISysRoleService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/system/user")
public class UserController {

    @Resource
    private IBaseUserService baseUserService;

    @Resource
    private ISysRoleService sysRoleService;

    @Resource
    private ISysMenuService sysMenuService;

    /**
     * 获取用户信息
     * @return 用户信息
     */
    @GetMapping("/info")
    public AjaxResult getUserInfo() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        BaseUser user = baseUserService.getById(userId);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setId(user.getUserId());
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setAvatar(user.getAvatar());

        List<SysRole> roles = sysRoleService.selectRolesByUserId(userId);
        userInfoVO.setRoles(roles.stream().map(SysRole::getRoleKey).collect(Collectors.toList()));

        Set<String> permissions = sysRoleService.selectPermsByUserId(userId);
        userInfoVO.setPermissions(permissions.stream().collect(Collectors.toList()));

        return AjaxResult.success(userInfoVO);
    }

    /**
     * 获取登陆用户的角色列表
     * @return 用户角色
     */
    @GetMapping("/roles")
    @PreAuthorize("hasRole('admin')") // 重要区别：hasRole() 会自动添加 ROLE_ 前缀hasAuthority() 直接匹配，不添加前缀
    public AjaxResult getUserRoles() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        List<SysRole> roles = sysRoleService.selectRolesByUserId(userId);
        return AjaxResult.success(roles);
    }

    /**
     * 获取当前用户的路由菜单
     * @return 路由菜单树
     */
    @GetMapping("/routes")
    public AjaxResult getUserRoutes() {
        Long userId = SecurityContextUtil.getCurrentUserId();
        List<RouteMenuVO> routes = sysMenuService.buildRouteMenuTreeByUserId(userId);
        return AjaxResult.success(routes);
    }
}
