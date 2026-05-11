package com.sym.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaptchaVO {
    /**
     * 验证码唯一标识（用于后续验证）
     */
    private String captchaKey;

    /**
     * 验证码图片的Base64字符串
     */
    private String captchaImage;
}
