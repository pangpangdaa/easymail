/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 80011
Source Host           : localhost:3306
Source Database       : t_mail

Target Server Type    : MYSQL
Target Server Version : 80011
File Encoding         : 65001

Date: 2018-10-29 11:03:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `mission`
-- ----------------------------
DROP TABLE IF EXISTS `mission`;
CREATE TABLE `mission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `content` text,
  `recv_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mission
-- ----------------------------

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `identity` varchar(255) DEFAULT NULL,
  `major` varchar(255) DEFAULT NULL,
  `class_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '夏超棂', '女', '22', '330206199612244628', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('2', '潘钊玮', '女', '22', '320102199607253228', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('3', '陈傲', '男', '22', '341226199610195779', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('4', '柯嘉倩', '女', '22', '330281199606110041', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('5', '罗央央', '女', '22', '331004199610312524', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('6', '曾佳阳', '女', '22', '360103199610202740', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('7', '夏菁', '女', '23', '330105199503031023', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('8', '苏雅', '女', '22', '420107199607191587', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('9', '沈君', '女', '22', '339005199602252123', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('10', '郑欣桐', '女', '23', '210211199512305829', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('11', '周园', '男', '22', '34290119961127661X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('12', '蔡彬茹', '女', '22', '33038219961121172X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('13', '闫宏伟', '男', '20', '341226199811136732', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('14', '夏超', '男', '25', '340822199305183110', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('15', '沈锞', '男', '22', '330683199607210014', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('16', '岑盛楠', '女', '23', '33028219951129426X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('17', '赵天恺', '男', '23', '120104199507293818', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('18', '孙潜昶', '男', '22', '330304199602040317', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('19', '陈姣', '女', '22', '330483199601055825', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('20', '刘小驰', '女', '30', '421122198807087725', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('21', '张凌鹏', '男', '24', '330724199403132000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('22', '姚钟玮', '男', '24', '33100419941024001X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('23', '魏环宇', '男', '21', '360121199710051911', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('24', '冉宜峰', '男', '22', '220402199605281439', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('25', '陈齐晋', '男', '22', '330327199608290012', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('26', '张晨飞', '男', '26', '411282199202063119', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('27', '范舰阳', '男', '23', '430602199508170518', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('28', '郑莹', '女', '22', '330621199601252660', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('29', '陈瑱怡', '女', '22', '330106199608152000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('30', '王诗慧', '女', '22', '330483199607250000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('31', '王萍萍', '女', '23', '350583199511250060', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('32', '徐依虹', '女', '23', '330621199512203023', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('33', '盛青青', '女', '23', '330521199506142329', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('34', '吕诗嘉', '女', '25', '330501199311171327', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('35', '俞俐', '女', '22', '330782199610300423', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('36', '赵丹', '女', '23', '330681199511280000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('37', '马陈棋', '女', '22', '330103199608151000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('38', '谢小璇', '女', '22', '330225199604203000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('39', '陈梦晗', '女', '22', '330326199605043000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('40', '金港', '男', '22', '33038219960616173X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('41', '陈子涵', '男', '22', '330681199606101000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('42', '彭英南', '男', '23', '412827199509033000', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('43', '张基', '男', '23', '330921199506273515', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('44', '陈鹿', '男', '22', '36242519960109561X', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('45', '邹珊珊', '女', '22', '330211199604200028', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('46', '王金子', '女', '21', '220621199709230222', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('47', '周琳', '女', '22', '330902199603200327', '金融专硕', '硕士二班');
INSERT INTO `user` VALUES ('48', '王婷', '女', '23', '653101199512090043', '金融专硕', '硕士二班');
