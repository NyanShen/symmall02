package com.sym.common.constants;

import java.util.Locale;

/**
 * 通用常量
 */
public class Constants {
    private Constants() {}
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 默认系统语言-简体中文
     */
    public static final Locale DEFAULT_LOCALE = Locale.SIMPLIFIED_CHINESE;

    /**
     * token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * token header
     */
    public static final String TOKEN_HEADER = "Authorization";
}
