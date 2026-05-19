package com.sym.system.service.impl;

import com.sym.common.exception.BusinessException;
import com.sym.system.domain.vo.CaptchaVO;
import com.sym.system.service.ICaptchaService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class CaptchaServiceImpl implements ICaptchaService {
    // 验证码缓存key前缀
    private static final String CAPTCHA_KEY_PREFIX = "captcha:";
    // 验证码过期时间- 5分钟
    private static final long CAPTCHA_EXPIRE_TIME = 5;

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造函数
     * @param redisTemplate RedisTemplate
     */
    public CaptchaServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 生成验证码
     * @return CaptchaVO
     * 查看base64图片验证码地址：https://img2base64.dazhishi.com/
     */
    @Override
    public CaptchaVO generateCaptcha() {
        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        captcha.setCharType(Captcha.TYPE_DEFAULT);

        String captchaKey = UUID.randomUUID().toString().replace("-", "");
        String captchaCode = captcha.text().toLowerCase();
        // 缓存验证码key, value, 过期时间, 单位分钟
        redisTemplate.opsForValue().set(
                CAPTCHA_KEY_PREFIX + captchaKey,
                captchaCode,
                CAPTCHA_EXPIRE_TIME,
                TimeUnit.MINUTES
        );
        return CaptchaVO.builder()
                .captchaKey(captchaKey)
                .captchaImage(captcha.toBase64())
                .build();
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captchaCode) {
        if (captchaKey == null || captchaCode == null) {
            throw new BusinessException("验证码不能为空");
        }

        String key = CAPTCHA_KEY_PREFIX + captchaKey;
        Object cachedCode = redisTemplate.opsForValue().get(key);

        if (cachedCode == null) {
            throw new BusinessException("验证码已过期，请重新获取");
        }

        redisTemplate.delete(key);

        // redis缓存的验证码 和 前端传递的验证码
        if (!cachedCode.toString().equalsIgnoreCase(captchaCode)) {
            throw new BusinessException("验证码错误");
        }

        return true;
    }
}
