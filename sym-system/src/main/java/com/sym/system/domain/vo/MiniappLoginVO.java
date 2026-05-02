package com.sym.system.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MiniappLoginVO {
    /**
     * 登录token
     */
    private String token;

}
