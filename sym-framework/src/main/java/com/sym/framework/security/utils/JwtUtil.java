package com.sym.framework.security.utils;

import com.sym.framework.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 通过密钥签名生成解析token，获取token中的信息，判断token是否过期
 */
@Component
public class JwtUtil {
    /** 生成密钥Key值 **/
    private static SecretKey key;

    /** 密钥的加密密钥串，过期时间 **/
    private static JwtProperties jwtProperties;

    /**
     * 构造方法注入
     * @param properties
     */
    @Autowired
    public JwtUtil(JwtProperties properties) {
        JwtUtil.jwtProperties = properties;
        JwtUtil.key = Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 使用方法 - 注入JwtProperties
     * 调用该方法时，将jwtProperties注入到JwtUtil中
     * @param jwtProperties
     * 在方法上标注 @Autowired 时，Spring 容器会在创建 JwtUtil Bean 的过程中：
     * 实例化 JwtUtil 对象
     * 检测到 setJwtProperties 方法上有 @Autowired 注解
     * 自动从容器中查找 JwtProperties 类型的 Bean
     * 自动调用 setJwtProperties 方法，将找到的 Bean 作为参数传入
     * 这就是 Spring 的方法级别依赖注入，不需要手动调用。
     */
//    @Autowired
//    public void setJwtProperties(JwtProperties jwtProperties) {
//        JwtUtil.jwtProperties = jwtProperties;
//    }

    /**
     * 初始化 JWT 密钥
     * @PostConstruct 标注的 init() 方法会在所有 @Autowired 注入完成后执行
     * <p>
     * 在 Bean 创建后自动执行，将配置文件中的密钥字符串转换为
     * HMAC-SHA 算法所需的 SecretKey 对象，用于后续的 Token
     * 签名和验证操作。
     * </p>
     */
//    @PostConstruct
//    public void init() {
//        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
//    }

    /**
     * 生成 JWT token
     */
    public static String generateToken(Long userId, String username) {
        // 设置多个claim
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        claims.put("userId", userId);

        return Jwts.builder()
                .subject(username) // 主题，即用户名，
                .claims(claims) // 添加自定义字段，.claim("userId", userId) // 自定义字段，用于存储用户ID，
                .issuedAt(new Date()) // 签发时间，
                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpire())) // 过期时间
                .signWith(key) // 签名
                .compact(); //  compact() 方法将 JWT 构建为字符串并返回
    }
    /**
     * 解析 JWT token
     * Claims：负载，包含用户信息，如用户ID、用户名、角色等
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key) // 验证签名
                .build() // 构建解析器
                .parseSignedClaims(token) // 解析 JWT
                .getPayload(); // 获取负载
    }

    /**
     * 获取token userId
     */
    public static Long getUserIdFromToken(String token) {
        Claims  claims = parseToken(token);
        return claims.get("userId", Long.class);
    }

    /**
     * 获取token username
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = parseToken(token);
        return claims.getSubject();
    }

    /**
     * 判断token是否过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /***
     * token 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception  e) {
            return false;
        }
    }
}
