package com.sym.common.enums;

public enum ResultCode {
    /**
     * 操作成功code, message
     */
    SUCCESS(0, "操作成功"),
    ERROR(501, "操作失败");
    // ....

    private final int code;
    private final String message;

    /**
     * 创建一个枚举对象
     * @param code
     * @param message
     */
    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
