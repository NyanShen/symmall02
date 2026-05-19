package com.sym.system.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String avatar;

    private List<String> roles;

    private List<String> permissions;

}
