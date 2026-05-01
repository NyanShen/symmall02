package com.sym.system.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 小程序用户扩展表
 */
@Data
@TableName("miniapp_user")
public class MiniappUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 关联主用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 微信openid
     */
    @TableField("openid")
    private String openid;

    /**
     * 微信unionid
     */
    @TableField("unionid")
    private String unionid;

    /**
     * 会话密钥
     */
    @TableField("session_key")
    private String sessionKey;

    /**
     * 国家
     */
    @TableField("country")
    private String country;

    /**
     * 省份
     */
    @TableField("province")
    private String province;

    /**
     * 城市
     */
    @TableField("city")
    private String city;

    /**
     * 语言
     */
    @TableField("language")
    private String language;

    /**
     * 最后登录时间
     */
    @TableField("last_login_time")
    private LocalDateTime lastLoginTime;
}
