package sym.com.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务异常基类
 *  * 所有自定义业务异常都继承此类
 */
@Data
@EqualsAndHashCode(callSuper = true) // callSuper: 是否继承父类属性
public class BusinessException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    public BusinessException() {
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 链式调用方式
     */
    public BusinessException setCode(int code) {
        this.code = code;
        return this;
    }

    public BusinessException setMessage(String message) {
        this.message = message;
        return this;
    }
}
