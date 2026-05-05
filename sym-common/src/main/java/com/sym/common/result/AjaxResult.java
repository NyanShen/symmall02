package com.sym.common.result;

import com.sym.common.enums.ResultCode;
import com.sym.common.utils.SymStringUtil;

import java.util.HashMap;

/**
 * 统一返回结果结构
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";


    private int code;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    /**
     * 构造函数
     * @param code
     * @param message
     */
    public AjaxResult(int code, String message) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, message);
    }

    /**
     * code ,message data
     */
    public AjaxResult(int code, String message, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, message);
        if (SymStringUtil.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return new AjaxResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage());
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static AjaxResult error() {
        return new AjaxResult(ResultCode.ERROR.getCode(), ResultCode.ERROR.getMessage());
    }

    public static AjaxResult error(String message) {
        return new AjaxResult(ResultCode.ERROR.getCode(), message);
    }

    public static AjaxResult error(int code, String message) {
        return new AjaxResult(code, message);
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

}
