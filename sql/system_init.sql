-- 初始化超级管理员：用户名 admin / 密码 admin123
INSERT INTO base_user (
    user_id, username, phone, email, password,
    nickname, avatar, gender, status, del_flag,
    create_time, update_time, remark
) VALUES (
             1,
             'admin',
             '13800000000',
             'admin@example.com',
             '$2a$10$q3CXkZDr3IBtmsqA0W76QeTTy1mAKPd2/SPYOSlXNZWje4/Mn6xai', -- 密码：123456
             '超级管理员',
             '',
             0,
             '0',
             '0',
             NOW(),
             NOW(),
             '系统初始化超级管理员'
         );

-- 关联后台管理员扩展表
INSERT INTO sys_user (
    id, user_id, real_name, employee_no, department, position,
    last_login_ip, last_login_time, pwd_update_time,
    create_by, update_by
) VALUES (
             1,
             1,
             '系统管理员',
             'ADMIN001',
             '总部-管理层',
             '超级管理员',
             '',
             NULL,
             NOW(),
             'admin',
             'admin'
         );

-- =============================================
-- 初始化角色数据
-- =============================================
INSERT INTO sys_role (
    role_id, role_name, role_key, role_sort, data_scope, status, del_flag, create_time, update_time, remark
) VALUES
      (1, '超级管理员', 'admin', 1, '1', '0', '0', NOW(), NOW(), '超级管理员'),
      (2, '普通管理员', 'common', 2, '2', '0', '0', NOW(), NOW(), '普通管理员'),
      (3, '小程序用户', 'miniapp_user', 3, '5', '0', '0', NOW(), NOW(), '小程序端用户');

-- =============================================
-- 初始化菜单权限数据
-- =============================================
INSERT INTO sys_menu (
    menu_id, menu_name, parent_id, order_num, path, component, menu_type, visible, status, perms, icon, create_time, update_time, remark
) VALUES
      -- 一级菜单
      (1, '系统管理', 0, 1, 'system', NULL, 'M', '0', '0', '', 'system', NOW(), NOW(), '系统管理目录'),
      (2, '用户管理', 0, 2, 'user', NULL, 'M', '0', '0', '', 'user', NOW(), NOW(), '用户管理目录'),
      (3, '角色管理', 0, 3, 'role', NULL, 'M', '0', '0', '', 'peoples', NOW(), NOW(), '角色管理目录'),
      (4, '菜单管理', 0, 4, 'menu', NULL, 'M', '0', '0', '', 'tree-table', NOW(), NOW(), '菜单管理目录'),

      -- 系统管理子菜单
      (100, '用户查询', 2, 1, '', '', 'F', '0', '0', 'system:user:query', '#', NOW(), NOW(), ''),
      (101, '用户新增', 2, 2, '', '', 'F', '0', '0', 'system:user:add', '#', NOW(), NOW(), ''),
      (102, '用户修改', 2, 3, '', '', 'F', '0', '0', 'system:user:edit', '#', NOW(), NOW(), ''),
      (103, '用户删除', 2, 4, '', '', 'F', '0', '0', 'system:user:remove', '#', NOW(), NOW(), ''),
      (104, '用户导出', 2, 5, '', '', 'F', '0', '0', 'system:user:export', '#', NOW(), NOW(), ''),

      (200, '角色查询', 3, 1, '', '', 'F', '0', '0', 'system:role:query', '#', NOW(), NOW(), ''),
      (201, '角色新增', 3, 2, '', '', 'F', '0', '0', 'system:role:add', '#', NOW(), NOW(), ''),
      (202, '角色修改', 3, 3, '', '', 'F', '0', '0', 'system:role:edit', '#', NOW(), NOW(), ''),
      (203, '角色删除', 3, 4, '', '', 'F', '0', '0', 'system:role:remove', '#', NOW(), NOW(), ''),
      (204, '角色导出', 3, 5, '', '', 'F', '0', '0', 'system:role:export', '#', NOW(), NOW(), ''),

      (300, '菜单查询', 4, 1, '', '', 'F', '0', '0', 'system:menu:query', '#', NOW(), NOW(), ''),
      (301, '菜单新增', 4, 2, '', '', 'F', '0', '0', 'system:menu:add', '#', NOW(), NOW(), ''),
      (302, '菜单修改', 4, 3, '', '', 'F', '0', '0', 'system:menu:edit', '#', NOW(), NOW(), ''),
      (303, '菜单删除', 4, 4, '', '', 'F', '0', '0', 'system:menu:remove', '#', NOW(), NOW(), '');

-- =============================================
-- 初始化超级管理员角色关联
-- =============================================
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);

-- =============================================
-- 初始化超级管理员拥有所有菜单权限
-- =============================================
INSERT INTO sys_role_menu (role_id, menu_id)
SELECT 1, menu_id FROM sys_menu;

-- =============================================
-- 初始化普通管理员权限（只读权限）
-- =============================================
INSERT INTO sys_role_menu (role_id, menu_id) VALUES
                                                 (2, 1), (2, 2), (2, 3), (2, 4),
                                                 (2, 100), (2, 200), (2, 300);