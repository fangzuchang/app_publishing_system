CREATE TABLE `administrator` (
  `id`         BIGINT(11)   NOT NULL,
  `name`       VARCHAR(255) NOT NULL,
  `realname`   VARCHAR(255) DEFAULT '',
  `password`   VARCHAR(255) NOT NULL,
  `createtime` DATETIME     DEFAULT NULL,
  `updatetime` DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator`
VALUES ('1', 'admin', '管理员', 'e10adc3949ba59abbe56e057f20f883e', '2017-07-10 11:13:10', '2017-07-10 11:13:14');

-- ----------------------------
-- Table structure for app
-- ----------------------------
DROP TABLE IF EXISTS `app`;
CREATE TABLE `app` (
  `app_id`         BIGINT(11) NOT NULL,
  `custId`         BIGINT(11) NOT NULL,
  `app_version`    VARCHAR(50)  DEFAULT '',
  `android_url`    VARCHAR(255) DEFAULT '',
  `iphone_url`     VARCHAR(255) DEFAULT '',
  `description`    VARCHAR(255) DEFAULT '',
  `createTimeDate` DATETIME     DEFAULT NULL,
  `updateTime`     DATETIME     DEFAULT NULL,
  `qrcode`         VARCHAR(255) DEFAULT '',
  `plistUrl`       VARCHAR(255) DEFAULT NULL,
  `iphPackageName` VARCHAR(255) DEFAULT NULL,
  `iphStoreUrl`    VARCHAR(255) DEFAULT NULL,
  `publishStyle`   VARCHAR(10)  DEFAULT NULL,
  `app_urls`       VARCHAR(255) DEFAULT '',
  PRIMARY KEY (`app_id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of app
-- ----------------------------
INSERT INTO `app` VALUES
  ('1780260825645204774', '7580516931901278355', 'v1.1', '/upload/app/1780260825645204774/android_v1.1_.apk', '',
                          '测试上传22', '2018-05-13 20:58:07', '2018-05-13 21:54:27',
                          'upload/app/1780260825645204774/1780260825645204774_v1.1_qrcode.jpg', '', '', '', '1', '');
INSERT INTO `app` VALUES
  ('4626753354972349282', '7580516931901278355', 'v1.0', '/upload/app/4626753354972349282/android_v1.0_.apk', '',
                          '测试上传', '2018-05-13 20:57:41', NULL,
                          'upload/app/4626753354972349282/4626753354972349282_v1.0_qrcode.jpg', '', '', '', '', '');

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id`         BIGINT(11)   NOT NULL,
  `custName`   VARCHAR(255) NOT NULL,
  `logoUrl`    VARCHAR(255) DEFAULT NULL,
  `appLogoUrl` VARCHAR(255) DEFAULT '',
  `createTime` DATETIME     DEFAULT NULL,
  `updateTime` DATETIME     DEFAULT NULL,
  PRIMARY KEY (`id`)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------
INSERT INTO `customer` VALUES ('7580516931901278355', '中原证券', 'upload/logo/7580516931901278355/zyzq_logo.jpg',
                               'upload/logo/7580516931901278355/zyzq_logo_app.png', '2018-05-13 20:55:14',
                               '2018-05-16 23:40:12');
