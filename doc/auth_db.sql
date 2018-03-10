CREATE DATABASE db_auth default character set utf8 collate utf8_general_ci;
USE db_auth;

/*######################################################################################
  系统信息(SSP,DSP,DMP等)
*/
CREATE TABLE `t_system` (
  `system_id`     int(11)      NOT NULL AUTO_INCREMENT COMMENT '系统ID',
  `system_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '系统名称',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200 AVG_ROW_LENGTH=2000;

/*######################################################################################
  平台信息（VPON-DSP， VPON-SSP, VPON-DMP.......）
*/
CREATE TABLE `t_platform` (
  `platform_id`   int(11)      NOT NULL AUTO_INCREMENT COMMENT '平台ID',
  `platform_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '平台名称',
  `secret_key`      varchar(256)     NOT NULL DEFAULT '' COMMENT '安全key',
  `system_id`     int(11)      NOT NULL DEFAULT 0 COMMENT '系统ID',
  `platform_domain` varchar(512)     NOT NULL DEFAULT '' COMMENT '平台域名',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=1000 AVG_ROW_LENGTH=3000;

/*######################################################################################
  用户信息
*/
CREATE TABLE `t_user` (
  `user_id`     int(11)           NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name`   varchar(128)      NOT NULL  UNIQUE COMMENT '用户名称',
  `password`    varchar(256)      NOT NULL DEFAULT '' COMMENT '用户密码',
  `status`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT '状态',
  `mobile_number` varchar(64)     NOT NULL DEFAULT '' COMMENT '电话号码',
  `contact_name`  varchar(256)    NOT NULL DEFAULT '' COMMENT '联系人',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200000 AVG_ROW_LENGTH=2000;

/*######################################################################################
  权限管理用户信息
*/
CREATE TABLE `t_admin_user` (
  `user_id`     int(11)           NOT NULL DEFAULT 0 COMMENT '用户ID',
  `user_name`   varchar(128)      NOT NULL  UNIQUE COMMENT '用户名称',
  `password`    varchar(256)      NOT NULL DEFAULT '' COMMENT '用户密码',
  `status`      int(10)        NOT NULL DEFAULT 0 COMMENT '状态 1 超级用户 2 系统权限维护人员',
  `mobile_number` varchar(64)     NOT NULL DEFAULT '' COMMENT '电话号码',
  `contact_name`  varchar(256)    NOT NULL DEFAULT '' COMMENT '联系人',  
  `system_id`     int(11)      NOT NULL DEFAULT 0 COMMENT '系统ID',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200000 AVG_ROW_LENGTH=2000;

/*######################################################################################
  角色信息
*/
CREATE TABLE `t_role` (
  `role_id`     int(11)         NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '角色名称',
  `platform_id`   int(11)      NOT NULL DEFAULT 0 COMMENT '平台ID',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=20000 AVG_ROW_LENGTH=2000;

/*######################################################################################
  用户角色信息（支持一个用户多角色）
*/
CREATE TABLE `t_user_role` (
  `user_id`     int(11)           NOT NULL DEFAULT 0 COMMENT '用户ID',
  `role_id`     int(11)            NOT NULL DEFAULT 0 COMMENT '角色ID',
  `platform_id`   int(11)      NOT NULL DEFAULT 0 COMMENT '平台ID',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `role_id`, `platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=1000;

/*######################################################################################
  权限信息
*/
CREATE TABLE `t_authority` (
  `auth_id`     int(11)           NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `auth_name`   varchar(512)      NOT NULL DEFAULT '' COMMENT '权限名称',
  `auth_f_id`   int(11)           NOT NULL DEFAULT 0 COMMENT '权限父ID',
  `system_id`     int(11)      NOT NULL DEFAULT 0 COMMENT '系统ID',
  `auth_level`     int(11)      NOT NULL DEFAULT 0 COMMENT '权限层级',
  `auth_f_tree` varchar(1024)     NOT NULL DEFAULT '' COMMENT '权限树',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=100000 AVG_ROW_LENGTH=2000;

/*######################################################################################
  角色权限信息
*/
CREATE TABLE `t_role_authority` (
  `role_id`     int(11)         NOT NULL DEFAULT 0 COMMENT '角色ID',
  `auth_id`     int(11)           NOT NULL DEFAULT 0 COMMENT '权限ID',
  `platform_id`     int(11)      NOT NULL DEFAULT 0 COMMENT '平台ID',
  `create_user_id` int(11) NOT NULL DEFAULT 0 COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`role_id`, `auth_id`, `platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=2000;

/*######################################################################################
  用户登录session会话
*/
CREATE TABLE `t_session` (
  `id`     int(11)           NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `platform_id`   int(11)      NOT NULL DEFAULT 0 COMMENT '平台ID',
  `auth_token_create_time` int(11)     NOT NULL DEFAULT 0 COMMENT '平台认证token创建时间',
  `auth_token_expire` int(11)     NOT NULL DEFAULT 0 COMMENT '平台认证token过期时间',
  `auth_token`     varchar(128)  NOT NULL DEFAULT '' COMMENT '平台认证token',
  `auth_token_used`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT '平台认证token使用标识',
  `open_id`        varchar(128)  NOT NULL DEFAULT '' COMMENT 'Open ID',
  `user_id`        int(11)  NOT NULL DEFAULT 0 COMMENT '用户ID',
  `code`           varchar(128)  NOT NULL DEFAULT '' COMMENT 'CODE',
  `code_create_time`    int(11)     NOT NULL DEFAULT 0 COMMENT 'CODE创建时间',
  `code_expire`    int(11)     NOT NULL DEFAULT 0 COMMENT 'CODE过期时间',
  `code_used`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT 'CODE使用标识',
  `access_token`   varchar(128)  NOT NULL DEFAULT '' COMMENT '访问TOKEN',
  `access_token_create_time`  int(11)     NOT NULL DEFAULT 0 COMMENT '访问TOKEN创建时间',
  `access_token_expire`  int(11)     NOT NULL DEFAULT 0 COMMENT '访问TOKEN过期时间',
  `access_token_used`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT '访问TOKEN使用标识',
  `refresh_token`   varchar(128)  NOT NULL DEFAULT '' COMMENT '刷新TOKEN',
  `refresh_token_create_time`  int(11)     NOT NULL DEFAULT 0 COMMENT '刷新TOKEN创建时间',
  `refresh_token_expire`  int(11)     NOT NULL DEFAULT 0 COMMENT '刷新TOKEN过期时间',
  `refresh_token_used`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT '刷新TOKEN使用标识',
  PRIMARY KEY (`id`),
  INDEX(`platform_id`, `auth_token`),
  INDEX(`platform_id`, `code`),
  INDEX(`platform_id`, `open_id`, `access_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200000 AVG_ROW_LENGTH=2000;

########################################################################################################################


########################################################################################################################

# CREATE TRIGGER FOR TABLE t_authority
DELIMITER &&
CREATE TRIGGER ADD_AUTHORITY_FOR_SYSTEM
before INSERT ON t_authority
FOR each row
BEGIN
  DECLARE var_auth_id                int(11);
  DECLARE var_auth_f_tree            varchar(1024);

  IF NEW.auth_level > 0 THEN
     SELECT auth_f_tree,auth_id INTO var_auth_f_tree,var_auth_id FROM t_authority WHERE auth_id = NEW.auth_f_id;
     SET NEW.auth_f_tree = CONCAT(var_auth_f_tree, "-", var_auth_id);
  END IF;
  IF NEW.auth_level = 0 or NEW.auth_f_id = 0 THEN
    SET NEW.auth_f_tree = "0";
  END IF;
END
&&
DELIMITER ;

########################################################################################################################

#insert into t_user (user_name, password, status, mobile_number, contact_name, group_id, description, create_user_id, update_time, create_time) value('admin@ziwow.com', 'f447b20a7fcbf53a5d5be013ea0b15af', 0, '13800000000', 'admin', 1,'', 1, 0, 0);
#insert into t_group (group_name, platform_id, description, create_user_id, update_time, create_time) value('default', 1, '', 1, 0, 0);
#insert into t_system (system_name, description, create_user_id, update_time, create_time) value ('ssp', '', 1, 0, 0);
#insert into t_platform (platform_name, secret_key, system_id, platform_domain, description, create_user_id, update_time, create_time) value ('system', '', 1, 'ssp.system.com', '', 1, 0, 0);
#insert into t_authority (auth_name, auth_f_id, auth_level, system_id, description, create_user_id, update_time, create_time) value ('商品管理', 0, 0, 1, '', 1, 0, 0);
#insert into t_role (role_name, platform_id, description, create_user_id, update_time, create_time) value ('CM', 1, '', 1, 0, 0);
#insert into t_role_authority (role_id, auth_id, create_user_id, update_time, create_time) value (1, 2, 1, 0, 0);
#insert into t_user_role (user_id, role_id, platform_id, create_user_id, update_time, create_time) value (1, 1, 1, 1, 0, 0);