DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
  id        BIGINT       NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  username  VARCHAR(16)  NOT NULL
  COMMENT '用户名称',
  password  VARCHAR(128) NOT NULL
  COMMENT '登录密码',
  nickname  VARCHAR(16)  NOT NULL
  COMMENT '昵称',
  status    INT          NOT NULL DEFAULT 1
  COMMENT '用户状态：0=正常，1=禁用',
  is_delete INT                   DEFAULT 0
  COMMENT '是否删除',
  role_id   BIGINT COMMENT '角色id',
  create_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT       NOT NULL,
  update_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_by BIGINT       NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT = '用户信息表';

DROP TABLE IF EXISTS role;

CREATE TABLE role
(
  id        BIGINT      NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  name      VARCHAR(50) NOT NULL
  COMMENT '名称',
  describes VARCHAR(255)         DEFAULT ''
  COMMENT '描述',
  status    INT                  DEFAULT 0
  COMMENT '状态（0—正常  1—禁用）',
  is_delete INT                  DEFAULT 0
  COMMENT '是否删除',
  create_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT      NOT NULL,
  update_at TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_by BIGINT      NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT '角色表';

DROP TABLE IF EXISTS role_permission;

CREATE TABLE role_permission
(
  id            BIGINT NOT NULL AUTO_INCREMENT
  COMMENT '主键',
  role_id       BIGINT NOT NULL
  COMMENT '角色主键',
  permission_id BIGINT NOT NULL
  COMMENT '权限主键',
  PRIMARY KEY (id)
)
  COMMENT '角色权限关联表';

DROP TABLE IF EXISTS sys_permission;

CREATE TABLE sys_permission
(
  id        BIGINT      NOT NULL                 AUTO_INCREMENT
  COMMENT '主键',
  parent_id BIGINT      NOT NULL                 DEFAULT 0
  COMMENT '父主键',
  code      VARCHAR(50) NOT NULL UNIQUE
  COMMENT '编码',
  name      VARCHAR(50) NOT NULL
  COMMENT '名称',
  is_menu   TINYINT     NOT NULL
  COMMENT '是否是菜单（0—否 1—是）',
  url       VARCHAR(255)                         DEFAULT ''
  COMMENT '地址',
  describes VARCHAR(255)                         DEFAULT ''
  COMMENT '描述',
  sort      INT                                  DEFAULT 0
  COMMENT '排序',
  create_at TIMESTAMP   NOT NULL                 DEFAULT CURRENT_TIMESTAMP,
  create_by BIGINT      NOT NULL,
  update_at TIMESTAMP   NOT NULL                 DEFAULT CURRENT_TIMESTAMP,
  update_by BIGINT      NOT NULL,
  PRIMARY KEY (id)
)
  COMMENT '权限表';
