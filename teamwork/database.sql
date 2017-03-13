/*
 Navicat Premium Data Transfer

 Source Server         : teamwork
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost
 Source Database       : teamwork

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : utf-8

 Date: 03/13/2017 15:58:01 PM
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `id` int(10) NOT NULL,
  `nick_name` varchar(128) NOT NULL COMMENT '昵称',
  `email` varchar(128) NOT NULL COMMENT '邮箱',
  `password` varchar(512) NOT NULL COMMENT '密码',
  `phone` varchar(16) DEFAULT NULL COMMENT '电话',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `has_del` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

SET FOREIGN_KEY_CHECKS = 1;
