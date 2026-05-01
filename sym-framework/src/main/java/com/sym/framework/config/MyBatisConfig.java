package com.sym.framework.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis 配置类
 * 配置扫描的mapper类
 */
@Configuration
@MapperScan("com.sym.**.mapper")
public class MyBatisConfig {
    /**
     * MyBatis-Plus 分页插件配置
     * 原理说明：
     * 1. MybatisPlusInterceptor 是 MyBatis-Plus 的核心拦截器链，用于管理各种内部拦截器。
     * 2. PaginationInnerInterceptor 是专门用于处理分页逻辑的内部拦截器。
     * 3. 当执行查询方法时，该拦截器会拦截 SQL 执行，自动解析并改写 SQL 语句，添加 LIMIT/OFFSET 等分页参数。
     * 4. DbType.MYSQL 指定数据库类型为 MySQL，确保生成符合 MySQL 语法的分页 SQL（如使用 LIMIT）。
     * 5. 通过 @Bean 注解将此拦截器注册为 Spring Bean，使其在 MyBatis 执行过程中生效。
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        return interceptor;
    }
}
