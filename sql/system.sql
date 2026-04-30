-- =============================================
-- 用户体系表结构（主用户表 + 分端扩展表）
-- =============================================

-- ----------------------------
-- 主用户表（所有端共享）
-- ----------------------------
DROP TABLE IF EXISTS base_user;
CREATE TABLE base_user (
                           user_id           BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '用户ID',
                           username          VARCHAR(50)     DEFAULT ''                 COMMENT '用户名（唯一标识）',
                           phone             VARCHAR(11)     DEFAULT ''                 COMMENT '手机号',
                           email             VARCHAR(50)     DEFAULT ''                 COMMENT '邮箱',
                           password          VARCHAR(100)    DEFAULT ''                 COMMENT '密码（小程序用户可为空）',
                           nickname          VARCHAR(50)     DEFAULT ''                 COMMENT '昵称',
                           avatar            VARCHAR(255)    DEFAULT ''                 COMMENT '头像',
                           gender            TINYINT(1)      DEFAULT 0                  COMMENT '性别（0未知 1男 2女）',
                           status            CHAR(1)         DEFAULT '0'                COMMENT '账号状态（0正常 1停用）',
                           del_flag          CHAR(1)         DEFAULT '0'                COMMENT '删除标志（0存在 2删除）',
                           create_time       DATETIME                                   COMMENT '创建时间',
                           update_time       DATETIME                                   COMMENT '更新时间',
                           remark            VARCHAR(500)    DEFAULT NULL               COMMENT '备注',
                           PRIMARY KEY (user_id) USING BTREE,
                           UNIQUE KEY `uk_username` (`username`),
                           KEY `idx_phone` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '主用户表';

-- ----------------------------
-- 后台管理员扩展表
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
                            id                BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '主键ID',
                            user_id           BIGINT(20)      NOT NULL                   COMMENT '关联主用户ID',
                            real_name         VARCHAR(50)     DEFAULT ''                 COMMENT '真实姓名',
                            employee_no       VARCHAR(50)     DEFAULT ''                 COMMENT '工号',
                            department        VARCHAR(100)    DEFAULT ''                 COMMENT '部门',
                            position          VARCHAR(100)    DEFAULT ''                 COMMENT '职位',
                            last_login_ip     VARCHAR(128)    DEFAULT ''                 COMMENT '最后登录IP',
                            last_login_time   DATETIME                                   COMMENT '最后登录时间',
                            pwd_update_time   DATETIME                                   COMMENT '密码最后更新时间',
                            create_by         VARCHAR(64)     DEFAULT ''                 COMMENT '创建者',
                            update_by         VARCHAR(64)     DEFAULT ''                 COMMENT '更新者',
                            PRIMARY KEY (id) USING BTREE,
                            UNIQUE KEY `uk_user_id` (`user_id`),
                            KEY `idx_employee_no` (`employee_no`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '后台管理员扩展表';

-- ----------------------------
-- 小程序用户扩展表
-- ----------------------------
DROP TABLE IF EXISTS miniapp_user;
CREATE TABLE miniapp_user (
                              id                BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '主键ID',
                              user_id           BIGINT(20)      NOT NULL                   COMMENT '关联主用户ID',
                              openid            VARCHAR(128)    NOT NULL                   COMMENT '微信openid',
                              unionid           VARCHAR(128)    DEFAULT ''                 COMMENT '微信unionid',
                              session_key       VARCHAR(255)    DEFAULT ''                 COMMENT '会话密钥',
                              country           VARCHAR(50)     DEFAULT ''                 COMMENT '国家',
                              province          VARCHAR(50)     DEFAULT ''                 COMMENT '省份',
                              city              VARCHAR(50)     DEFAULT ''                 COMMENT '城市',
                              language          VARCHAR(20)     DEFAULT ''                 COMMENT '语言',
                              last_login_time   DATETIME                                   COMMENT '最后登录时间',
                              PRIMARY KEY (id) USING BTREE,
                              UNIQUE KEY `uk_user_id` (`user_id`),
                              UNIQUE KEY `uk_openid` (`openid`),
                              KEY `idx_unionid` (`unionid`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '小程序用户扩展表';
