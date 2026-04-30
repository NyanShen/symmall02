package sym.com.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串工具类
 */
public class SymStringUtil extends StringUtils {
    private SymStringUtil() {
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }
}
