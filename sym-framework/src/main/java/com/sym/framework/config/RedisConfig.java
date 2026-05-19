package com.sym.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 * @author sym
 * @description 配置后才能找到RedisTemplate
 * 📋 代码整体作用
 * 这是一个 RedisTemplate 的自定义配置类，用于替代 Spring Boot 默认的 RedisTemplate 配置，提供更合理的序列化策略。
 */
@Configuration
public class RedisConfig {
    /**
     *
     * @param connectionFactory RedisConnectionFactory 由 Spring Boot 自动提供（基于 application.yml 中的 redis 配置）
     * @return RedisTemplate<String, Object> - Key 为 String，Value 为 Object 的通用模板
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // StringRedisSerializer 处理 String 类型数据  直接存储字符串，无额外编码
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        // Jackson2JsonRedisSerializer 处理 Object 类型数据  将数据序列化为 JSON 字符串
        GenericJackson2JsonRedisSerializer jsonSerializer = new GenericJackson2JsonRedisSerializer();

        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(jsonSerializer);
        template.setHashKeySerializer(stringSerializer);
        template.setHashValueSerializer(jsonSerializer);

        template.afterPropertiesSet();
        return template;
    }
}
