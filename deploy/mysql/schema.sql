-- 个人网站数据库
-- 数据表：
-- 用户表 t_user
-- 数据表 t_video t_music t_photo t_book t_essay
DROP DATABASE IF EXISTS `island`;
CREATE SCHEMA `island` DEFAULT CHARACTER SET utf8 ;

-- t_user
DROP TABLE IF EXISTS `island`.`t_user`;
CREATE TABLE `island`.`t_user` (
  `uid` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(32) NOT NULL,
  `password` VARCHAR(64) NOT NULL,
  `introduction` VARCHAR(512) DEFAULT NULL,
  `phone` VARCHAR(16) NOT NULL,
  `email` VARCHAR(32) NOT NULL,
  `role` INT(4) DEFAULT 1 COMMENT '0-管理员 1-普通用户',
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '上次更新时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `username_unique` (`username`) USING BTREE
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

LOCK TABLES `island`.`t_user` WRITE ;
INSERT INTO `island`.`t_user`(username, password, introduction, phone, email, role, create_time, update_time)
VALUES
  ('Admin', '123456789','The website manager','13760241875', 'xiaohuileee@gmail.com', 0, now(), now()),
  ('Arvin', '123456', 'The demo normal customers', '13760241875', '1633199394@qq.com', 1, now(), now());
UNLOCK TABLES;

-- t_video
DROP TABLE IF EXISTS `island`.`t_video`;
CREATE TABLE `island`.`t_video` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;


-- t_music
DROP TABLE IF EXISTS `island`.`t_music`;
CREATE TABLE `island`.`t_music` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- t_photo
DROP TABLE IF EXISTS `island`.`t_photo`;
CREATE TABLE `island`.`t_photo` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- t_book
DROP TABLE IF EXISTS `island`.`t_book`;
CREATE TABLE `island`.`t_book` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;

-- t_essay
DROP TABLE IF EXISTS `island`.`t_essay`;
CREATE TABLE `island`.`t_essay` (
  `id` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;