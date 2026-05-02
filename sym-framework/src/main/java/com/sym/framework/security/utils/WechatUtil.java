package com.sym.framework.security.utils;

// 解析微信返回的 JSON 数据
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
/**
 * @Value：读取配置文件中的值
 * ResponseEntity：HTTP 响应封装
 * @Component：Spring 组件注解
 * RestTemplate：HTTP 客户端
 */
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信小程序工具类（用于调用微信 API）
 * 发送微信请求，解析相应数据
 */
@Slf4j
@Component
public class WechatUtil {
    // 微信 API 地址
    private static final String CODE2SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session";

    @Value("${wechat.miniapp.appid:}")
    private String appid;

    @Value("${wechat.miniapp.secret:}")
    private String secret;

    // 使用 RestTemplate， 用于发送 HTTP 请求
    private final RestTemplate restTemplate = new RestTemplate();
    // 使用 Jackson， 于 JSON 序列化/反序列化
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 小程序登录
     * @param code
     * @return 微信登录接口返回的 JSON 数据
     */
    public Map<String, String> code2Session(String code) {
        try {
            String url = String.format("%s?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code",
                    CODE2SESSION_URL, appid, secret, code);
            // 发送 GET 请求到微信服务器，获取响应
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            // 将响应的 JSON 数据解析为 JsonNode 对象
            JsonNode jsonNode = objectMapper.readTree(response.getBody());

            // 检查响应中是否包含错误码
            if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
                log.error("微信登录失败: errcode={}, errmsg={}",
                        jsonNode.get("errcode").asInt(),
                        jsonNode.get("errmsg").asText());
                throw new RuntimeException("微信登录失败: " + jsonNode.get("errmsg").asText());
            }
            Map<String, String> result = new HashMap<>();
            result.put("openid", jsonNode.get("openid").asText());
            if (jsonNode.has("unionid")) {
                result.put("unionid", jsonNode.get("unionid").asText());
            }
            result.put("session_key", jsonNode.get("session_key").asText());
            return result;
        } catch (Exception e) {
            log.error("调用微信API异常", e);
            throw new RuntimeException("获取微信用户信息失败", e);
        }
    }
}
