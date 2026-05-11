package com.sym.system.service;

import com.sym.system.domain.vo.CaptchaVO;

/**
 * 验证码服务接口
 */
public interface ICaptchaService {

    CaptchaVO generateCaptcha();

    boolean validateCaptcha(String captchaKey, String captchaCode);
}
