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


-- ----------------------------
-- 角色表
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
                          role_id           BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '角色ID',
                          role_name         VARCHAR(30)     NOT NULL                   COMMENT '角色名称',
                          role_key          VARCHAR(100)    NOT NULL                   COMMENT '角色权限字符串',
                          role_sort         INT(4)          NOT NULL                   COMMENT '显示顺序',
                          data_scope        CHAR(1)         DEFAULT '1'                COMMENT '数据范围（1全部 2自定义 3本部门 4本部门及以下 5仅本人）',
                          status            CHAR(1)         NOT NULL DEFAULT '0'       COMMENT '角色状态（0正常 1停用）',
                          del_flag          CHAR(1)         DEFAULT '0'                COMMENT '删除标志（0存在 2删除）',
                          create_time       DATETIME                                   COMMENT '创建时间',
                          update_time       DATETIME                                   COMMENT '更新时间',
                          remark            VARCHAR(500)    DEFAULT NULL               COMMENT '备注',
                          PRIMARY KEY (role_id) USING BTREE,
                          UNIQUE KEY `uk_role_key` (`role_key`)
) ENGINE=InnoDB AUTO_INCREMENT=100 COMMENT = '角色表';

-- ----------------------------
-- 菜单权限表
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
                          menu_id           BIGINT(20)      NOT NULL AUTO_INCREMENT    COMMENT '菜单ID',
                          menu_name         VARCHAR(50)     NOT NULL                   COMMENT '菜单名称',
                          parent_id         BIGINT(20)      DEFAULT 0                  COMMENT '父菜单ID',
                          order_num         INT(4)          DEFAULT 0                  COMMENT '显示顺序',
                          path              VARCHAR(200)    DEFAULT ''                 COMMENT '路由地址',
                          component         VARCHAR(255)    DEFAULT NULL               COMMENT '组件路径',
                          query             VARCHAR(255)    DEFAULT NULL               COMMENT '路由参数',
                          is_frame          TINYINT(1)      DEFAULT 1                  COMMENT '是否为外链（0是 1否）',
                          is_cache          TINYINT(1)      DEFAULT 0                  COMMENT '是否缓存（0缓存 1不缓存）',
                          menu_type         CHAR(1)         DEFAULT ''                 COMMENT '菜单类型（M目录 C菜单 F按钮）',
                          visible           CHAR(1)         DEFAULT '0'                COMMENT '菜单状态（0显示 1隐藏）',
                          status            CHAR(1)         DEFAULT '0'                COMMENT '菜单状态（0正常 1停用）',
                          perms             VARCHAR(100)    DEFAULT NULL               COMMENT '权限标识',
                          icon              VARCHAR(100)    DEFAULT '#'                COMMENT '菜单图标',
                          create_time       DATETIME                                   COMMENT '创建时间',
                          update_time       DATETIME                                   COMMENT '更新时间',
                          remark            VARCHAR(500)    DEFAULT ''                 COMMENT '备注',
                          PRIMARY KEY (menu_id) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1000 COMMENT = '菜单权限表';

-- ----------------------------
-- 用户和角色关联表（一个用户可以有多个角色）
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
                               user_id           BIGINT(20)      NOT NULL                   COMMENT '用户ID',
                               role_id           BIGINT(20)      NOT NULL                   COMMENT '角色ID',
                               PRIMARY KEY (user_id, role_id) USING BTREE,
                               KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB COMMENT = '用户和角色关联表';

-- ----------------------------
-- 角色和菜单关联表（一个角色可以有多个菜单权限）
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
                               role_id           BIGINT(20)      NOT NULL                   COMMENT '角色ID',
                               menu_id           BIGINT(20)      NOT NULL                   COMMENT '菜单ID',
                               PRIMARY KEY (role_id, menu_id) USING BTREE,
                               KEY `idx_menu_id` (`menu_id`)
) ENGINE=InnoDB COMMENT = '角色和菜单关联表';