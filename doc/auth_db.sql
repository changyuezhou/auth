CREATE DATABASE db_auth default character set utf8 collate utf8_general_ci;
USE db_auth;

CREATE TABLE `t_system` (
  `system_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '系统ID',
  `system_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '系统名称',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`system_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200 AVG_ROW_LENGTH=2000;


CREATE TABLE `t_platform` (
  `platform_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `platform_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '平台名称',
  `secret_key`      varchar(256)     NOT NULL DEFAULT '' COMMENT '安全key',
  `system_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '系统ID',
  `platform_domain` varchar(512)     NOT NULL DEFAULT '' COMMENT '平台域名',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`platform_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=1000 AVG_ROW_LENGTH=3000;


CREATE TABLE `t_user` (
  `user_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
  `user_name`   varchar(128)      NOT NULL  DEFAULT '' COMMENT '用户名称',
  `password`    varchar(256)      NOT NULL DEFAULT '' COMMENT '用户密码',
  `status`      int(10)        NOT NULL DEFAULT 0 COMMENT '状态 1 超级用户 2 系统权限维护人员',
  `mobile_number` varchar(64)     NOT NULL DEFAULT '' COMMENT '电话号码',
  `contact_name`  varchar(256)    NOT NULL DEFAULT '' COMMENT '联系人',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_group` (
  `group_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组ID',
  `group_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '组名称',
  `platform_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=200 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_user_group` (
  `user_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
  `group_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=1000;

CREATE TABLE `t_group_authority` (
  `group_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组ID',
  `auth_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '权限ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`group_id`, `auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_role` (
  `role_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '角色ID',
  `role_name`   varchar(256)     NOT NULL DEFAULT '' COMMENT '角色名称',
  `platform_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=20000 AVG_ROW_LENGTH=2000;


CREATE TABLE `t_user_role` (
  `user_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
  `role_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '角色ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=1000;


CREATE TABLE `t_authority` (
  `auth_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '权限ID',
  `auth_name`   varchar(512)      NOT NULL DEFAULT '' COMMENT '权限名称',
  `url`   varchar(512)      NOT NULL DEFAULT '' COMMENT '访问路径',
  `auth_f_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '权限父ID',
  `system_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '系统ID',
  `auth_level`     int(11)      NOT NULL DEFAULT 0 COMMENT '权限层级',
  `auth_f_tree` varchar(8096)     NOT NULL DEFAULT '' COMMENT '权限树',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=100000 AVG_ROW_LENGTH=2000;


CREATE TABLE `t_role_authority` (
  `role_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '角色ID',
  `auth_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '权限ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`role_id`, `auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_organization` (
  `organization_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组织ID',
  `organization_name`   varchar(512)      NOT NULL DEFAULT '' COMMENT '组织名称',
  `organization_f_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '权限父ID',
  `platform_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `organization_level`     int(11)      NOT NULL DEFAULT 0 COMMENT '组织层级',
  `organization_f_tree` varchar(8096)     NOT NULL DEFAULT '' COMMENT '组织树',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=100000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_organization_authority` (
  `organization_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组织ID',
  `auth_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '权限ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`organization_id`, `auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_user_organization` (
  `user_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
  `organization_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '组织ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `organization_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=1000;
/*
CREATE TABLE `t_custom_arch` (
  `custom_arch_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '定制结构ID',
  `custom_arch_name`   varchar(512)      NOT NULL DEFAULT '' COMMENT '定制结构名称',
  `custom_arch_f_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '定制结构父ID',
  `platform_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `flag`     int(11)      NOT NULL DEFAULT 0 COMMENT '定制结构类型',
  `custom_arch_level`     int(11)      NOT NULL DEFAULT 0 COMMENT '定制结构层级',
  `custom_arch_f_tree` varchar(8096)     NOT NULL DEFAULT '' COMMENT '定制结构树',
  `description` varchar(1024)     NOT NULL DEFAULT '' COMMENT '描述信息',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`custom_arch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=100000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_custom_arch_authority` (
  `custom_arch_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '定制结构ID',
  `auth_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '权限ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`custom_arch_id`, `auth_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=2000;

CREATE TABLE `t_user_custom_arch` (
  `user_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
  `custom_arch_id`     varchar(128)      NOT NULL DEFAULT '' COMMENT '定制结构ID',
  `create_user_id` varchar(128)      NOT NULL DEFAULT '' COMMENT '创建者ID',
  `update_time` bigint NOT NULL DEFAULT 0 COMMENT '更新时间',
  `create_time` bigint NOT NULL DEFAULT 0 COMMENT '创建时间',
  PRIMARY KEY (`user_id`, `custom_arch_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 MAX_ROWS=400000 AVG_ROW_LENGTH=1000;
*/
CREATE TABLE `t_session` (
  `id`     int(11)           NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `platform_id`   varchar(128)      NOT NULL DEFAULT '' COMMENT '平台ID',
  `auth_token_create_time` int(11)     NOT NULL DEFAULT 0 COMMENT '平台认证token创建时间',
  `auth_token_expire` int(11)     NOT NULL DEFAULT 0 COMMENT '平台认证token过期时间',
  `auth_token`     varchar(128)  NOT NULL DEFAULT '' COMMENT '平台认证token',
  `auth_token_used`      tinyint(1)        NOT NULL DEFAULT 0 COMMENT '平台认证token使用标识',
  `open_id`        varchar(128)  NOT NULL DEFAULT '' COMMENT 'Open ID',
  `user_id`        varchar(128)      NOT NULL DEFAULT '' COMMENT '用户ID',
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

DELIMITER &&
CREATE VIEW `v_user_authority` AS (SELECT
                               a.user_id, a.user_name, b.auth_id, b.auth_name, c.platform_id, c.platform_name,
                               d.create_user_id, f.user_name as create_user_name, d.update_time, d.create_time
                          FROM t_user a, t_authority b, t_platform c, t_user_group d, t_group_authority e, t_user f, t_group g
                          WHERE a.user_id = d.user_id and d.group_id = e.group_id and e.auth_id= b.auth_id 
                          and d.group_id = g.group_id and c.platform_id = g.platform_id and d.create_user_id=f.user_id) UNION
                          (SELECT
                               a.user_id, a.user_name, b.auth_id, b.auth_name, c.platform_id, c.platform_name,
                               d.create_user_id, f.user_name as create_user_name, d.update_time, d.create_time
                          FROM t_user a, t_authority b, t_platform c, t_user_role d, t_role_authority e, t_user f, t_role g
                          WHERE a.user_id = d.user_id and d.role_id = e.role_id and e.auth_id= b.auth_id 
                          and d.role_id = g.role_id and c.platform_id = g.platform_id and d.create_user_id=f.user_id) UNION
                          (SELECT
                               a.user_id, a.user_name, b.auth_id, b.auth_name, c.platform_id, c.platform_name,
                               d.create_user_id, f.user_name as create_user_name, d.update_time, d.create_time
                          FROM t_user a, t_authority b, t_platform c, t_user_organization d, t_organization_authority e, t_user f, t_organization g
                          WHERE a.user_id = d.user_id and d.organization_id = e.organization_id and e.auth_id= b.auth_id 
                          and d.organization_id = g.organization_id and c.platform_id = g.platform_id and d.create_user_id=f.user_id)                          
&&
DELIMITER ;

########################################################################################################################

# CREATE TRIGGER FOR TABLE t_authority
DELIMITER &&
CREATE TRIGGER ADD_AUTHORITY_FOR_SYSTEM
before INSERT ON t_authority
FOR each row
BEGIN
  DECLARE var_auth_id                varchar(1024);
  DECLARE var_auth_f_tree            varchar(1024);

  IF NEW.auth_level > 0 THEN
     SELECT auth_f_tree,auth_id INTO var_auth_f_tree,var_auth_id FROM t_authority WHERE auth_id = NEW.auth_f_id;
     SET NEW.auth_f_tree = CONCAT(var_auth_f_tree, "-", var_auth_id);
  END IF;
  IF NEW.auth_level = 1 THEN
    SET NEW.auth_f_tree = NEW.auth_f_id;
  END IF;  
END
&&
DELIMITER ;

# CREATE TRIGGER FOR TABLE t_organization
DELIMITER &&
CREATE TRIGGER ADD_ORGANIZATION_FOR_PLATFORM
before INSERT ON t_organization
FOR each row
BEGIN
  DECLARE var_org_id                varchar(1024);
  DECLARE var_org_f_tree            varchar(1024);

  IF NEW.organization_level > 0 THEN
     SELECT organization_f_tree,organization_id INTO var_org_f_tree,var_org_id FROM t_organization WHERE organization_id = NEW.organization_f_id;
     SET NEW.organization_f_tree = CONCAT(var_org_f_tree, "-", var_org_id);
  END IF;
  IF NEW.organization_level = 1 THEN
    SET NEW.organization_f_tree = NEW.organization_f_id;
  END IF;
END
&&
DELIMITER ;

# CREATE TRIGGER FOR TABLE t_custom_arch
DELIMITER &&
CREATE TRIGGER ADD_CUSTOM_ARCH_FOR_PLATFORM
before INSERT ON t_custom_arch
FOR each row
BEGIN
  DECLARE var_cus_arch_id                varchar(1024);
  DECLARE var_cus_arch_f_tree            varchar(1024);

  IF NEW.custom_arch_level > 0 THEN
     SELECT custom_arch_f_tree,custom_arch_id INTO var_cus_arch_f_tree,var_cus_arch_id FROM t_custom_arch WHERE custom_arch_id = NEW.custom_arch_f_id;
     SET NEW.custom_arch_f_tree = CONCAT(var_cus_arch_f_tree, "-", var_cus_arch_id);
  END IF;
  IF NEW.custom_arch_level = 1 THEN
    SET NEW.custom_arch_f_tree = NEW.custom_arch_f_id;
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