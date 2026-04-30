package com.sym.framework.security.utils;

import com.sym.framework.config.JwtProperties;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    private JwtUtil jwtUtil;
    private JwtProperties jwtProperties;

    @BeforeEach
    public void setUp() {
        jwtProperties = new JwtProperties();
        jwtProperties.setSecret("test_jwt_secret_key_for_unit_testing_must_be_long_enough_2026");
        jwtProperties.setExpire(3600000L);

        jwtUtil = new JwtUtil(jwtProperties);

        assertNotNull(jwtProperties, "JwtProperties应该被正确初始化");
        assertNotNull(jwtUtil, "JwtUtil应该被正确初始化");
    }

    @Test
    public void testGenerateToken() {
        String token = jwtUtil.generateToken(1L, "admin");

        assertNotNull(token, "生成的token不应为null");
        assertFalse(token.isEmpty(), "生成的token不应为空字符串");

        System.out.println("生成的Token: " + token);
    }

    @Test
    public void testParseToken() {
        String token = jwtUtil.generateToken(1L, "admin");

        Claims claims = jwtUtil.parseToken(token);

        assertNotNull(claims, "解析的claims不应为null");
        assertEquals("admin", claims.getSubject(), "用户名应该匹配");
        assertEquals(1L, claims.get("userId", Long.class), "用户ID应该匹配");
        assertEquals("admin", claims.get("username", String.class), "自定义字段用户名应该匹配");
    }

    @Test
    public void testGetUserIdFromToken() {
        Long userId = 123L;
        String token = jwtUtil.generateToken(userId, "testuser");

        Long extractedUserId = jwtUtil.getUserIdFromToken(token);

        assertEquals(userId, extractedUserId, "从token中提取的用户ID应该匹配");
    }

    @Test
    public void testGetUsernameFromToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(1L, username);

        String extractedUsername = jwtUtil.getUsernameFromToken(token);

        assertEquals(username, extractedUsername, "从token中提取的用户名应该匹配");
    }

    @Test
    public void testIsTokenExpired() {
        String token = jwtUtil.generateToken(1L, "admin");

        boolean expired = jwtUtil.isTokenExpired(token);

        assertFalse(expired, "新生成的token不应该过期");
    }

    @Test
    public void testValidateToken() {
        String token = jwtUtil.generateToken(1L, "admin");

        boolean isValid = jwtUtil.validateToken(token);

        assertTrue(isValid, "新生成的token应该是有效的");
    }

    @Test
    public void testValidateTokenWithInvalidToken() {
        String invalidToken = "invalid.token.here";

        boolean isValid = jwtUtil.validateToken(invalidToken);

        assertFalse(isValid, "无效的token应该返回false");
    }

    @Test
    public void testTokenWithDifferentUsers() {
        String token1 = jwtUtil.generateToken(1L, "user1");
        String token2 = jwtUtil.generateToken(2L, "user2");

        assertNotEquals(token1, token2, "不同用户生成的token应该不同");

        Long userId1 = jwtUtil.getUserIdFromToken(token1);
        Long userId2 = jwtUtil.getUserIdFromToken(token2);

        assertEquals(1L, userId1, "第一个token的用户ID应该是1");
        assertEquals(2L, userId2, "第二个token的用户ID应该是2");
    }
}
