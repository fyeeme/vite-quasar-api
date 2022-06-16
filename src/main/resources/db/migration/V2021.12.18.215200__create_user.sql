CREATE TABLE `user` (
  `id` INT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
  `nickname` VARCHAR(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户昵称',
  `password` VARCHAR(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `phone` VARCHAR(32) DEFAULT NULL COMMENT '手机号',
  `email` VARCHAR(64) DEFAULT NULL COMMENT '邮箱',
  `avatar` VARCHAR(200) DEFAULT NULL COMMENT '头像',
  `enabled` TINYINT(1) NOT NULL DEFAULT '1' COMMENT '是否有效，1-是，0-否',
  `locked` TINYINT(1) NOT NULL DEFAULT '0' COMMENT '是否锁定，1-是，0-否',
  `created_by` int DEFAULT NULL COMMENT '创建者用户ID',
  `modified_by` int DEFAULT NULL COMMENT '修改者用户ID',
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  `modified_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后更新时间',
  PRIMARY KEY (`id`)
);

CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `name` varchar(64) NOT NULL COMMENT '角色名称',
  `code` varchar(64) NOT NULL COMMENT '角色编码',
  PRIMARY KEY (`id`)
);

CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户角色关系ID',
  `user_id` int NOT NULL COMMENT '用户ID',
  `role_id` int NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
);

CREATE TABLE `privilege` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '权限名称',
  `code` varchar(64) NOT NULL COMMENT '权限编码',
  `model` varchar(64) NOT NULL COMMENT '模块名称',
  `description` varchar(200) NOT NULL COMMENT '权限说明',
  PRIMARY KEY (`id`)
);

CREATE TABLE `role_privilege` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `privilege_id` bigint NOT NULL COMMENT '权限ID',
  PRIMARY KEY (`role_id`, `privilege_id`)
);