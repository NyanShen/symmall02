package com.sym.framework.security.utils;

import org.junit.jupiter.api.Test;

public class PasswordUtilTest {

    @Test
    public void testEncode() {
        String password = "123456";
        String encode = PasswordUtil.encode(password);
        System.out.println(encode);
    }

    @Test
    public void testMatch() {
        String password = "123456";
        String encode = PasswordUtil.encode(password);
        boolean match = PasswordUtil.matches(password, encode);
        System.out.println(match);
    }


}
