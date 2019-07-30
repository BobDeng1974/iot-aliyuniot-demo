SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for about
-- ----------------------------
DROP TABLE IF EXISTS `about`;
CREATE TABLE `about`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `status` int(1) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of about
-- ----------------------------
INSERT INTO `about` VALUES (6, '关于我们', '<p>\n    <span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 18px;\">——“老有所依，智创物联！”</span>\n</p>\n<p>\n    <span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; font-size: 18px;\"><br/></span>\n</p>\n<p style=\"text-align: center;\">\n    <span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\"><span style=\"color: rgb(102, 102, 102); font-family: &quot;Microsoft Yahei&quot;, &quot;PingFang SC&quot;, Avenir, &quot;Segoe UI&quot;, &quot;Hiragino Sans GB&quot;, STHeiti, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-align: center;\">Copyright&nbsp;© 2018 -&nbsp;</span><span style=\"color: rgb(102, 102, 102); font-family: &quot;Microsoft Yahei&quot;, &quot;PingFang SC&quot;, Avenir, &quot;Segoe UI&quot;, &quot;Hiragino Sans GB&quot;, STHeiti, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-align: center;\">2020</span><span style=\"color: rgb(102, 102, 102); font-family: &quot;Microsoft Yahei&quot;, &quot;PingFang SC&quot;, Avenir, &quot;Segoe UI&quot;, &quot;Hiragino Sans GB&quot;, STHeiti, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-align: center;\">&nbsp;</span><span style=\"color: rgb(102, 102, 102); font-family: &quot;Microsoft Yahei&quot;, &quot;PingFang SC&quot;, Avenir, &quot;Segoe UI&quot;, &quot;Hiragino Sans GB&quot;, STHeiti, &quot;Microsoft Sans Serif&quot;, &quot;WenQuanYi Micro Hei&quot;, sans-serif; font-size: 14px; text-align: center;\"><span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; text-align: center;\">湖北大学物联网研究所</span>. All Rights Reserved</span></span>\n</p>\n<p style=\"text-align: center;\">\n    <span style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;;\"><span style=\"color: rgb(102, 102, 102); font-size: 14px; font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; text-align: center;\">iot.thunk.cn 版权所有</span></span>\n</p>', NULL, NULL, '2019-06-11 22:23:05');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '文章内容',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效  1.有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '发布号作者表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES (5, '莎士比亚', '2017-10-25 09:08:45', '2017-10-30 17:59:41', '1');
INSERT INTO `article` VALUES (6, '亚里士多德', '2017-10-26 10:49:28', '2017-11-18 09:54:15', '1');
INSERT INTO `article` VALUES (10, '亚历山大', '2017-10-26 14:57:45', '2017-11-08 13:28:52', '1');
INSERT INTO `article` VALUES (11, '李白', '2017-10-26 15:23:42', '2017-10-26 15:23:42', '1');
INSERT INTO `article` VALUES (19, '文章test2', '2017-11-18 13:37:07', '2017-11-18 13:37:11', '1');

-- ----------------------------
-- Table structure for device_data
-- ----------------------------
DROP TABLE IF EXISTS `device_data`;
CREATE TABLE `device_data`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '序列号',
  `iot_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '设备ID',
  `data` json NOT NULL COMMENT '设备数据（JSON）',
  `create_time` datetime(0) NOT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '入库时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `deviceid`(`iot_id`) USING BTREE COMMENT '设备id'
) ENGINE = InnoDB AUTO_INCREMENT = 45954 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '设备数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission`  (
  `id` int(11) NOT NULL DEFAULT 0 COMMENT '自定id,主要供前端展示权限列表分类排序使用.',
  `menu_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '归属菜单,前端判断并展示菜单使用,',
  `menu_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '菜单的中文释义',
  `permission_code` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '权限的代码/通配符,对应代码中@RequiresPermissions 的value',
  `permission_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '本权限的中文释义',
  `required_permission` tinyint(1) NULL DEFAULT 2 COMMENT '是否本菜单必选权限, 1.必选 2非必选 通常是\"列表\"权限是必选',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台权限表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES (101, 'about', '文章管理', 'about:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (102, 'about', '文章管理', 'about:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (103, 'about', '文章管理', 'about:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (104, 'about', '文章管理', 'about:delete', '删除', 2);
INSERT INTO `sys_permission` VALUES (201, 'user', '用户管理', 'user:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (202, 'user', '用户管理', 'user:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (203, 'user', '用户管理', 'user:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (301, 'role', '角色管理', 'role:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (302, 'role', '角色管理', 'role:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (303, 'role', '角色管理', 'role:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (304, 'role', '角色管理', 'role:delete', '删除', 2);
INSERT INTO `sys_permission` VALUES (401, 'product', '产品管理', 'product:list', '查询产品列表', 1);
INSERT INTO `sys_permission` VALUES (402, 'product', '产品管理', 'product:detail', '查询产品详情', 2);
INSERT INTO `sys_permission` VALUES (403, 'product', '产品管理', 'product:add', '添加产品', 2);
INSERT INTO `sys_permission` VALUES (404, 'product', '产品管理', 'product:update', '更新产品信息', 2);
INSERT INTO `sys_permission` VALUES (405, 'product', '产品管理', 'product:delete', '删除产品', 2);
INSERT INTO `sys_permission` VALUES (501, 'device', '设备管理', 'device:allStatus', '查询所有设备状态', 1);
INSERT INTO `sys_permission` VALUES (502, 'device', '设备管理', 'device:list', '查询产品下的设备', 1);
INSERT INTO `sys_permission` VALUES (503, 'device', '设备管理', 'device:allList', '查询所有设备列表', 2);
INSERT INTO `sys_permission` VALUES (504, 'device', '设备管理', 'device:status', '查询指定设备运行状态', 1);
INSERT INTO `sys_permission` VALUES (505, 'device', '设备管理', 'device:detail', '查询设备详情', 2);
INSERT INTO `sys_permission` VALUES (506, 'device', '设备管理', 'device:add', '添加设备', 2);
INSERT INTO `sys_permission` VALUES (507, 'device', '设备管理', 'device:delete', '删除设备', 2);
INSERT INTO `sys_permission` VALUES (601, 'permission', '权限管理', 'permission:list', '列表', 1);
INSERT INTO `sys_permission` VALUES (602, 'permission', '权限管理', 'permission:update', '修改', 2);
INSERT INTO `sys_permission` VALUES (603, 'permission', '权限管理', 'permission:delete', '删除', 2);
INSERT INTO `sys_permission` VALUES (604, 'permission', '权限管理', 'permission:add', '新增', 2);
INSERT INTO `sys_permission` VALUES (701, 'system', '系统信息', 'system:select', '获取系统信息', 1);
INSERT INTO `sys_permission` VALUES (801, 'group', '房间管理', 'group:add', '创建房间', 2);
INSERT INTO `sys_permission` VALUES (802, 'group', '房间管理', 'group:update', '修改房间信息', 2);
INSERT INTO `sys_permission` VALUES (803, 'group', '房间管理', 'group:delete', '删除房间', 2);
INSERT INTO `sys_permission` VALUES (804, 'group', '房间管理', 'group:batchAddDevice', '批量添加设备到房间', 2);
INSERT INTO `sys_permission` VALUES (805, 'group', '房间管理', 'group:batchDeleteDevice', '批量从房间删除设备', 2);
INSERT INTO `sys_permission` VALUES (806, 'group', '房间管理', 'group:detail', '查看房间详情', 2);
INSERT INTO `sys_permission` VALUES (807, 'group', '房间管理', 'group:list', '查询房间列表', 1);
INSERT INTO `sys_permission` VALUES (808, 'group', '房间管理', 'group:deviceList', '查询房间中的设备列表', 2);
INSERT INTO `sys_permission` VALUES (809, 'group', '房间管理', 'group:deviceByGroup', '查询某一设备所在的房间列表', 2);
INSERT INTO `sys_permission` VALUES (901, 'alarm', '告警管理', 'alarm:list', '告警信息管理', 1);

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台角色表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '管理员', '2019-05-12 13:24:34', '2019-05-12 14:24:52', '1');
INSERT INTO `sys_role` VALUES (5, 'test', '2019-05-13 13:42:25', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role` VALUES (6, 'guest0', '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role` VALUES (7, 'guest', '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0),
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0),
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效 1有效     2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 214 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色-权限关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES (23, 5, 101, '2019-05-13 13:42:25', '2019-05-17 17:23:18', '2');
INSERT INTO `sys_role_permission` VALUES (24, 5, 102, '2019-05-13 13:42:25', '2019-05-17 17:23:18', '2');
INSERT INTO `sys_role_permission` VALUES (25, 5, 103, '2019-05-13 13:42:25', '2019-05-17 17:23:18', '2');
INSERT INTO `sys_role_permission` VALUES (26, 5, 201, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (27, 5, 301, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (28, 5, 401, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (29, 5, 501, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (30, 5, 504, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (31, 5, 503, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (32, 5, 601, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (33, 5, 701, '2019-05-16 11:21:58', '2019-05-16 11:21:58', '1');
INSERT INTO `sys_role_permission` VALUES (34, 5, 502, '2019-05-16 11:26:35', '2019-05-16 11:26:35', '1');
INSERT INTO `sys_role_permission` VALUES (35, 5, 202, '2019-05-17 17:23:18', '2019-05-17 17:57:02', '2');
INSERT INTO `sys_role_permission` VALUES (36, 5, 203, '2019-05-17 17:23:18', '2019-05-17 17:57:02', '2');
INSERT INTO `sys_role_permission` VALUES (37, 5, 302, '2019-05-17 17:23:18', '2019-05-17 17:57:02', '2');
INSERT INTO `sys_role_permission` VALUES (38, 5, 402, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (39, 5, 403, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (40, 5, 505, '2019-05-17 17:23:18', '2019-05-17 17:58:47', '2');
INSERT INTO `sys_role_permission` VALUES (41, 5, 506, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (42, 5, 801, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (43, 5, 802, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (44, 5, 804, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (45, 5, 805, '2019-05-17 17:23:18', '2019-06-05 19:35:18', '2');
INSERT INTO `sys_role_permission` VALUES (46, 5, 806, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (47, 5, 807, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (48, 5, 808, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (49, 5, 809, '2019-05-17 17:23:18', '2019-05-17 17:23:18', '1');
INSERT INTO `sys_role_permission` VALUES (50, 5, 803, '2019-05-17 17:23:18', '2019-06-05 19:35:18', '2');
INSERT INTO `sys_role_permission` VALUES (51, 5, 303, '2019-05-17 17:40:23', '2019-05-17 17:55:38', '2');
INSERT INTO `sys_role_permission` VALUES (52, 5, 304, '2019-05-17 17:40:23', '2019-05-17 17:55:38', '2');
INSERT INTO `sys_role_permission` VALUES (53, 5, 101, '2019-05-17 17:54:05', '2019-05-17 17:54:21', '2');
INSERT INTO `sys_role_permission` VALUES (54, 5, 102, '2019-05-17 17:54:05', '2019-05-17 17:54:21', '2');
INSERT INTO `sys_role_permission` VALUES (55, 5, 103, '2019-05-17 17:54:05', '2019-05-17 17:54:21', '2');
INSERT INTO `sys_role_permission` VALUES (56, 5, 505, '2019-05-17 17:59:43', '2019-05-17 17:59:43', '1');
INSERT INTO `sys_role_permission` VALUES (57, 6, 101, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (58, 6, 201, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (59, 6, 301, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (60, 6, 401, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (61, 6, 402, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (62, 6, 501, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (63, 6, 502, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (64, 6, 504, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (65, 6, 505, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (66, 6, 503, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (67, 6, 601, '2019-06-05 19:32:31', '2019-06-09 17:23:41', '2');
INSERT INTO `sys_role_permission` VALUES (68, 6, 701, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (69, 6, 807, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (70, 6, 806, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (71, 6, 808, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (72, 6, 809, '2019-06-05 19:32:31', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (73, 6, 901, '2019-06-05 19:32:31', '2019-06-05 19:32:31', '1');
INSERT INTO `sys_role_permission` VALUES (74, 5, 101, '2019-06-05 19:35:18', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role_permission` VALUES (75, 5, 103, '2019-06-05 19:35:18', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role_permission` VALUES (76, 5, 102, '2019-06-05 19:35:18', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role_permission` VALUES (77, 5, 404, '2019-06-05 19:35:18', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role_permission` VALUES (78, 5, 901, '2019-06-05 19:35:18', '2019-06-05 19:35:18', '1');
INSERT INTO `sys_role_permission` VALUES (79, 7, 101, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (80, 7, 201, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (81, 7, 301, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (82, 7, 401, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (83, 7, 402, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (84, 7, 403, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (85, 7, 404, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (86, 7, 501, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (87, 7, 502, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (88, 7, 504, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (89, 7, 503, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (90, 7, 505, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (91, 7, 506, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (92, 7, 601, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (93, 7, 701, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (94, 7, 801, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (95, 7, 807, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (96, 7, 802, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (97, 7, 804, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (98, 7, 806, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (99, 7, 808, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (100, 7, 809, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (101, 7, 901, '2019-06-05 19:36:26', '2019-06-05 19:36:26', '1');
INSERT INTO `sys_role_permission` VALUES (102, 5, 602, '2019-06-05 19:36:54', '2019-06-07 20:24:16', '2');
INSERT INTO `sys_role_permission` VALUES (103, 5, 604, '2019-06-05 19:36:54', '2019-06-07 20:24:16', '2');
INSERT INTO `sys_role_permission` VALUES (104, 5, 104, '2019-06-05 19:42:36', '2019-06-05 19:43:46', '2');
INSERT INTO `sys_role_permission` VALUES (105, 6, 102, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (106, 6, 103, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (107, 6, 202, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (108, 6, 203, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (109, 6, 302, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (110, 6, 303, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (111, 6, 403, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (112, 6, 404, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (113, 6, 506, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (114, 6, 801, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (115, 6, 802, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (116, 6, 804, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (117, 6, 805, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (118, 6, 803, '2019-06-09 16:53:10', '2019-06-09 17:06:00', '2');
INSERT INTO `sys_role_permission` VALUES (119, 6, 103, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (120, 6, 202, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (121, 6, 102, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (122, 6, 203, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (123, 6, 303, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (124, 6, 302, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (125, 6, 402, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (126, 6, 403, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (127, 6, 404, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (128, 6, 503, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (129, 6, 505, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (130, 6, 506, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (131, 6, 801, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (132, 6, 802, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (133, 6, 804, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (134, 6, 806, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (135, 6, 808, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (136, 6, 809, '2019-06-09 17:06:41', '2019-06-09 17:07:58', '2');
INSERT INTO `sys_role_permission` VALUES (137, 6, 102, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (138, 6, 103, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (139, 6, 202, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (140, 6, 302, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (141, 6, 303, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (142, 6, 402, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (143, 6, 403, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (144, 6, 404, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (145, 6, 503, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (146, 6, 505, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (147, 6, 506, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (148, 6, 801, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (149, 6, 802, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (150, 6, 804, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (151, 6, 806, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (152, 6, 808, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (153, 6, 809, '2019-06-09 17:10:21', '2019-06-09 17:11:54', '2');
INSERT INTO `sys_role_permission` VALUES (154, 6, 102, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (155, 6, 103, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (156, 6, 202, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (157, 6, 302, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (158, 6, 303, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (159, 6, 402, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (160, 6, 403, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (161, 6, 404, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (162, 6, 503, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (163, 6, 505, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (164, 6, 506, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (165, 6, 801, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (166, 6, 802, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (167, 6, 804, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (168, 6, 806, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (169, 6, 808, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (170, 6, 809, '2019-06-09 17:13:05', '2019-06-09 17:14:32', '2');
INSERT INTO `sys_role_permission` VALUES (171, 6, 102, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (172, 6, 103, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (173, 6, 202, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (174, 6, 302, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (175, 6, 303, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (176, 6, 203, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (177, 6, 402, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (178, 6, 403, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (179, 6, 404, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (180, 6, 503, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (181, 6, 505, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (182, 6, 506, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (183, 6, 801, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (184, 6, 802, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (185, 6, 804, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (186, 6, 806, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (187, 6, 808, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (188, 6, 809, '2019-06-09 17:20:56', '2019-06-09 17:23:01', '2');
INSERT INTO `sys_role_permission` VALUES (189, 6, 102, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (190, 6, 103, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (191, 6, 202, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (192, 6, 203, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (193, 6, 302, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (194, 6, 303, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (195, 6, 402, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (196, 6, 403, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (197, 6, 404, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (198, 6, 503, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (199, 6, 505, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (200, 6, 506, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (201, 6, 801, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (202, 6, 802, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (203, 6, 804, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (204, 6, 806, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (205, 6, 808, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (206, 6, 809, '2019-06-09 17:23:41', '2019-06-09 17:25:00', '2');
INSERT INTO `sys_role_permission` VALUES (207, 6, 601, '2019-06-09 17:25:00', '2019-06-09 17:25:00', '1');
INSERT INTO `sys_role_permission` VALUES (208, 6, 402, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');
INSERT INTO `sys_role_permission` VALUES (209, 6, 503, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');
INSERT INTO `sys_role_permission` VALUES (210, 6, 505, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');
INSERT INTO `sys_role_permission` VALUES (211, 6, 806, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');
INSERT INTO `sys_role_permission` VALUES (212, 6, 808, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');
INSERT INTO `sys_role_permission` VALUES (213, 6, 809, '2019-06-13 09:31:15', '2019-06-13 09:31:15', '1');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `role_id` int(11) NULL DEFAULT 0 COMMENT '角色ID',
  `create_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) COMMENT '创建时间',
  `update_time` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `delete_status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '1' COMMENT '是否有效  1有效  2无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10018 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '运营后台用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10003, 'admin', '123456', '超级管理员', 1, '2019-05-03 11:52:38', '2019-06-05 19:26:28', '1');
INSERT INTO `sys_user` VALUES (10011, 'test', '123456', '测试', 5, '2019-05-17 17:23:38', '2019-06-05 19:37:28', '2');
INSERT INTO `sys_user` VALUES (10012, 'test', 'test', '测试', 5, '2019-06-05 19:37:48', '2019-06-28 09:48:42', '1');
INSERT INTO `sys_user` VALUES (10013, 'guest', '20192019', '访客', 6, '2019-06-05 19:38:09', '2019-06-13 09:32:30', '1');
INSERT INTO `sys_user` VALUES (10014, 'superguest', 'wuliansuo1209', '超级访客', 7, '2019-06-05 19:38:38', '2019-06-05 19:38:38', '1');
INSERT INTO `sys_user` VALUES (10015, 'ydsguest', '123456', '研电赛访客', 6, '2019-06-09 17:08:30', '2019-06-09 17:10:01', '2');
INSERT INTO `sys_user` VALUES (10016, 'ydsguest', '123456', '研电赛访客', 6, '2019-06-09 17:15:02', '2019-06-09 17:21:17', '2');
INSERT INTO `sys_user` VALUES (10017, 'ydsguest', '123456', '研电赛访客', 6, '2019-06-09 17:25:30', '2019-06-13 09:29:57', '2');

SET FOREIGN_KEY_CHECKS = 1;
