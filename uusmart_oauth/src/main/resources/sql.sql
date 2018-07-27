CREATE TABLE `oauth_approvals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expiresAt` timestamp NULL DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `lastModifiedAt` timestamp NULL DEFAULT NULL,
  `userId` varchar(45) DEFAULT NULL,
  `clientId` varchar(45) DEFAULT NULL,
  `scope` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `oc_developer` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(128) NOT NULL DEFAULT '' COMMENT 'app key',
  `client_secret` varchar(128) NOT NULL DEFAULT '' COMMENT 'App Secret',
  `client_name` varchar(128) NOT NULL DEFAULT '' COMMENT '应用名称',
  `icon_uri` varchar(128) NOT NULL DEFAULT '' COMMENT '应用图标的存放地址(可选)',
  `redirect_uri` varchar(128) NOT NULL DEFAULT '' COMMENT 'code的回调地址',
  `grant_types` varchar(128) NOT NULL DEFAULT '' COMMENT '授予的认证模式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `description` varchar(128) NOT NULL DEFAULT '' COMMENT '开发应用的描述信息',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '记录状态 (1.正常 2.禁用)',
  `platform` tinyint(4) NOT NULL DEFAULT '0' COMMENT '平台类型（1.pc机 2.app 3.第三方插件）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='客户端-服务端 开发调测表';

INSERT INTO `oc_developer` (`id`,`client_id`,`client_secret`,`client_name`,`icon_uri`,`redirect_uri`,`grant_types`,`create_time`,`description`,`status`,`platform`) VALUES (1,'58c51cba-87a3-4d19-a34f-45731383ba86','824e8f60-d85e-4a39-8742-dd1d779b35b1','authorization_code','www.baidu.com','http://www.baidu.com','authorization_code, refresh_token','2016-03-07 00:00:00','authorization_code',1,1);