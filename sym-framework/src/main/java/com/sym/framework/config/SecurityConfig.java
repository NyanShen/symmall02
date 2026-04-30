package com.sym.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Spring Security 配置
 * 应用启动流程：
 * ┌─────────────────────────────────────────────┐
 * │ 1. Spring容器启动，扫描@Configuration类      │
 * └─────────────────────────────────────────────┘
 *                     ↓
 * ┌─────────────────────────────────────────────┐
 * │ 2. 解析SecurityConfig，注册@Bean定义        │
 * │    - securityFilterChain (方法定义)         │
 * │    - authenticationManager                  │
 * │    - passwordEncoder                        │
 * └─────────────────────────────────────────────┘
 *                     ↓
 * ┌─────────────────────────────────────────────┐
 * │ 3. Spring Security自动配置类执行             │
 * │    @EnableWebSecurity → WebSecurityConfiguration │
 * └─────────────────────────────────────────────┘
 *                     ↓
 * ┌─────────────────────────────────────────────┐
 * │ 4. WebSecurityConfiguration需要构建过滤器链 │
 * │    调用：getBean(SecurityFilterChain.class) │
 * └─────────────────────────────────────────────┘
 *                     ↓
 * ┌─────────────────────────────────────────────┐
 * │ 5. Spring发现SecurityFilterChain的Bean定义  │
 * │    调用securityFilterChain()方法创建实例     │
 * └─────────────────────────────────────────────┘
 *
 */
@Configuration
@EnableWebSecurity // 开启 Spring web Security, 默认情况下，Spring Security 会拦截所有请求，并执行认证和授权操作。
@EnableMethodSecurity(prePostEnabled = true) // 开启 Spring Security 的启用方法级安全注解, prePostEnabled
public class SecurityConfig {
    /**
     * 配置 Spring Security 过滤链
     * <p>
     * 定义安全策略，包括禁用 CSRF、设置无状态会话、配置公开访问路径
     * 以及注册 JWT 认证过滤器。
     * </p>
     *
     * @param http HttpSecurity 对象，用于配置 Web 安全策略
     * @return 构建完成的 SecurityFilterChain 实例
     * @throws Exception 配置过程中可能抛出的异常
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                //作用：关闭跨站攻击防护
                .csrf(AbstractHttpConfigurer::disable) // 禁用 csrf 跨站请求伪造
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 配置 HTTP 请求的授权规则
                .authorizeHttpRequests(auth -> auth
                        // 登录接口 → 无需登录认证
                        .requestMatchers("/auth/login", "/auth/register", "wx/login").permitAll()
                        // 静态资源 → 无需登录认证
                        .requestMatchers("/index/**", "/static/**").permitAll()
                        // 其余所有接口 → 必须登录认证
                        .anyRequest().authenticated());
        return http.build();
    }

    /**
     * authenticationManager 认证管理器
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws  Exception{
        return config.getAuthenticationManager();
    }

    /**
     * 密码加密解密
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
