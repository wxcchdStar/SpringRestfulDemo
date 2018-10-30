# 用户
INSERT INTO user (id, username, password, nickname, status, create_at, create_by, update_at, update_by) VALUES (1, 'admin', '$2a$10$uqTbFPXaPrl5jj/A7zY5lOk99ACJShohIOAB34ErfSAtfhRHpJMWa', '超级管理员', 0, DEFAULT, 0, DEFAULT, 0);
# 权限
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (100, 0, 'organization', '机构管理', 1, '', null, 1, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (110, 100, 'user', '用户管理', 1, '/user', null, 1, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (111, 110, 'user_list', '列表', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (112, 110, 'user_add', '添加', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (113, 110, 'user_edit', '编辑', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (114, 110, 'user_delete', '删除', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (120, 100, 'role', '角色管理', 1, '/role', null, 2, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (121, 120, 'role_list', '列表', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (122, 120, 'role_add', '添加', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (123, 120, 'role_edit', '编辑', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (124, 120, 'role_delete', '删除', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);
INSERT INTO sys_permission (id, parent_id, code, name, is_menu, url, describes, sort, create_at, create_by, update_at, update_by) VALUES (125, 120, 'role_grant', '授权', 0, null, null, 0, DEFAULT, 0, DEFAULT, 0);