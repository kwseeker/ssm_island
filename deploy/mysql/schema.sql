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
  ('Admin', '5C961E58C50EBE808916BA3205200265','The website manager','13760241875', 'xiaohuileee@gmail.com', 0, now(), now()),
  ('Arvin', '63D1E796F67243D56C40D4605CE74270', 'The demo normal customers', '13760241875', '1633199394@qq.com', 1, now(), now());
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

-- t_essay_content
DROP TABLE IF EXISTS `island`.`t_essay_content`;
CREATE TABLE `island`.`t_essay_content` (
  `cid` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '随笔编号',
  `author_id` INT(11) UNSIGNED DEFAULT '0' COMMENT '作者ID',
  `type` VARCHAR(16) DEFAULT 'post' COMMENT '随笔类型', -- 用于日后拓展自定义随笔类型
  `content` text COMMENT '随笔正文',
  `hits` INT(11) unsigned DEFAULT '0' COMMENT '点击量',
  `access_authority` VARCHAR(8) DEFAULT 'personal'  COMMENT '访问权限',   -- 访问权限：all(全部可见) logined(登录可见) personal(自己可见)
  `create_time` DATETIME NOT NULL COMMENT '创建时间',
  `update_time` DATETIME NOT NULL COMMENT '上次更新时间',
  PRIMARY KEY (`cid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;
LOCK TABLES `island`.`t_essay_content` WRITE ;
INSERT INTO `island`.`t_essay_content`(author_id, content, access_authority, create_time, update_time)
VALUES
  ('1', '### Welcome to essay module!\r\n\r\n### ...\r\n\r\n...', 'all', now(), now());
UNLOCK TABLES;

-- t_essay_comment
DROP TABLE IF EXISTS `island`.`t_essay_comment`;
CREATE TABLE `island`.`t_essay_comment` (
  `comid` INT(11) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `cid` INT(11) UNSIGNED DEFAULT '0' COMMENT '所属随笔编号',
  `commentator` VARCHAR(32) DEFAULT NULL COMMENT '评论人用户名',
  `commentator_id` INT(11) UNSIGNED DEFAULT '0' COMMENT '评论人用户编号',
  `content` text COMMENT '评论内容',
  `create_time` DATETIME NOT NULL COMMENT '评论时间',
  PRIMARY KEY (`comid`),
  KEY `cid` (`cid`)
) ENGINE = InnoDB DEFAULT CHARACTER SET = utf8;
LOCK TABLES `island`.`t_essay_comment` WRITE ;
INSERT INTO `island`.`t_essay_comment`(cid, commentator, commentator_id, content, create_time)
VALUES
  ('1', 'Admin', '1', '### Welcome to essay module!\r\n\r\n### ...\r\n\r\n...', now());
UNLOCK TABLES;