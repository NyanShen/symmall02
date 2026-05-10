package com.sym.framework.security.filter;

import com.sym.framework.security.service.IPermissionService;
import com.sym.framework.security.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

/**
 * JWT认证过滤器
 * 继承OncePerRequestFilter确保每个请求只执行一次过滤
 */
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 即使没有实现也不会报错, 这样就完美解决了循环依赖问题，同时保持了良好的架构设计！
    @Autowired(required = false)
    private IPermissionService permissionService;

    /**
     * 执行JWT token验证
     *
     * @param request HTTP请求对象
     * @param response HTTP响应对象
     * @param filterChain 过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException IO异常
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getTokenFromRequest(request);
            if (StringUtils.hasText(token) && JwtUtil.validateToken(token)) {
                Long userId = JwtUtil.getUserIdFromToken(token);
                String username = JwtUtil.getUsernameFromToken(token);
                // 403Forbidden权限不足 尽管客户端提供了有效的鉴权凭证，但无权访问该资源，因此被禁止访问
                // 需要将token放入springContext中，才能被识别认证成功。
                Map<String, Object> userDetails = new HashMap<>();
                userDetails.put("userId", userId);
                userDetails.put("username", username);
                /**
                 * 用户权限
                 */
                List<SimpleGrantedAuthority> authorities = new ArrayList<>();

                try {
                    // 角色
                    List<String> roleKeys = permissionService.getRoleKeysByUserId(userId);
                    for (String roleKey : roleKeys) {
                        // 加载角色时，添加 ROLE_ 前缀
                        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleKey));
                        // 例如：roleKey = "admin" → Authority = "ROLE_admin"
                    }
                    // 权限
                    Set<String> perms = permissionService.getPermsByUserId(userId);
                    for (String perm : perms) {
                        // 加载权限时，直接使用权限标识
                        authorities.add(new SimpleGrantedAuthority(perm));
                        // 例如：perm = "system:user:query" → Authority = "system:user:query"

                    }
                } catch (Exception e) {
                    log.warn("加载用户权限失败，用户ID: {}", userId, e);
                }

                /**
                 * 构造函数的参数是：
                 * 第一个参数（principal）：userDetails - 这是主体信息-识别"谁"登录了
                 * 第二个参数（credentials）：null - 凭证（密码），认证后通常设为null
                 * 第三个参数（authorities）：new ArrayList<>() - 权限列表-判断"能做什么"
                 * getDetails()--附加信息（IP、时间等） setDetails() 方法- 记录的额外信息
                 */
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                log.debug("用户认证成功，用户ID: {}, 用户名: {}, 权限数量: {}", userId, username, authorities.size());
            }
        } catch (Exception e) {
            log.error("JWT验证失败", e);
        }
        // 继续处理下一个过滤器
        filterChain.doFilter(request, response);
    }

    /**
     * 从请求头中提取JWT token
     *
     * @param request HTTP请求对象
     * @return JWT token字符串
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
