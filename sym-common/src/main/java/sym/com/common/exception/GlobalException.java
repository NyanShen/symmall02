package sym.com.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sym.com.common.result.AjaxResult;
import sym.com.common.utils.SymStringUtil;

/**
 * @RestControllerAdvice = @ControllerAdvice + @ResponseBody
 * 作用：全局捕获 SpringMVC DispatcherServlet 处理过程中抛出的所有异常，并统一返回 JSON 格式。
 * 请求 → DispatcherServlet
 *        → HandlerMapping 找到对应 Controller
 *        → HandlerAdapter 执行方法
 *            ↓
 * 【Controller 方法内抛出异常】
 *            ↓
 * DispatcherServlet 检测到异常
 *            ↓
 * 遍历所有 `@RestControllerAdvice/ExceptionHandler`
 *            ↓
 * 找到**最匹配**的异常处理方法
 *            ↓
 * 执行该方法，返回 JSON → 前端
 */
@Slf4j
@RestControllerAdvice
public class GlobalException extends RuntimeException{
    /**
     * 原因：
     * 如果没有显式定义，Java编译器会根据类结构自动生成一个 serialVersionUID（基于字段、方法等计算）。类一旦修改，自动生成的UID就会变化，导致新旧版本不兼容。
     * 版本号被"记住"的时机：
     * 序列化写入时 → 永久保存在目标介质中
     * 反序列化读取时 → 对比介质中的UID和当前类UID
     * 存储介质包括：文件、数据库、Session、消息队列、缓存等
     * 生命周期：从写入那一刻起，直到被删除或重新序列化
     * 核心要点：
     * 版本号一旦被序列化到某个介质中，就会永久存在（除非删除文件/数据）
     * 修改类时需要考虑历史上已存储的所有数据
     * 这就是为什么商业项目升级时，需要谨慎处理序列化版本兼容性
     * 记忆口诀："序列化时写进去，反序列化时比一比，存储介质永铭记，修改类时要留意"
     */
    private static final long serialVersionUID = 1L;
    /**
     * 空构造方法，避免反序列化问题
     */
    public GlobalException() { }

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleBusinessException(BusinessException e) {
        log.warn("业务异常: code={}, message={}", e.getCode(), e.getMessage());
        return AjaxResult.error(e.getCode(), e.getMessage());
    }

    /**
     * @RequestBody 参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public AjaxResult handleValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String msg = SymStringUtil.isNull(fieldError) ? "参数错误" : fieldError.getDefaultMessage();
        log.warn("参数校验异常：{}", msg);
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), msg);
    }

    /**
     * 方法不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        String msg = "不支持 " + e.getMethod() + " 请求方式";
        log.warn(msg);
        return AjaxResult.error(HttpStatus.BAD_REQUEST.value(), msg);
    }
    /**
     * 兜底异常（必须最后)
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("系统异常：{}", e.getMessage());
        String msg = SymStringUtil.isEmpty(e.getMessage()) ? "服务器繁忙，请稍后重试" : e.getMessage();
        return AjaxResult.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg);
    }

}
