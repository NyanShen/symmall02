package com.sym.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data // 生成getter/setter
@Component // 添加到spring容器中
@ConfigurationProperties(prefix = "jwt") // 读取application.yml中的jwt节点
public class JwtProperties {
    /**
     * 密钥
     * 默认值：symmall_jwt_secret_key_2026_this_is_a_long_enough_secret_for_hs256
     * 密钥长度必须大于16位
     * 密钥长度越长，越安全
     * 加载配置时，会自动替换默认值
     */
    private String secret = "symmall_jwt_secret_key_2026_this_is_a_long_enough_secret_for_hs256";
    /**
     * 过期时间
     * 默认值：7 天
     */
    private Long expire = 7 * 24 * 60 * 60 * 1000L;
}
