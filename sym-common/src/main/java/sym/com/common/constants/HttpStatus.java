package sym.com.common.constants;

/**
 *  http 业务异常状态码
 */
public class HttpStatus {
    private HttpStatus() {
    }
    public static final int SUCCESS = 200;
    public static final int ERROR = 500;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int REQUEST_TIMEOUT = 408;
    public static final int UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int BAD_GATEWAY = 502;
    public static final int SERVICE_UNAVAILABLE = 503;
    public static final int GATEWAY_TIMEOUT = 504;
}
