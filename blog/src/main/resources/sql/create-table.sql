-- use lfz;
-- set NAMES utf8mb4;
--
CREATE TABLE IF NOT EXISTS blog (
  id int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  title varchar(50) NOT NULL DEFAULT '' COMMENT '博客标题',
  real_content TEXT NOT NULL COMMENT '文章的实际内容，md',
  show_content TEXT NOT NULL COMMENT '文章显示的内容，html',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  index idx_title(title)
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='博客表';

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_name` varchar(10) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码，用户名为盐，五次md5',
  `roles` varchar(20) NOT NULL DEFAULT '' COMMENT '用户角色，逗号分隔',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_idx_role_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';