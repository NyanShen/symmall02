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