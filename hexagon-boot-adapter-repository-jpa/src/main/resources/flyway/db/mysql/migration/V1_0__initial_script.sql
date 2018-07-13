/*

 Source Server         : 1-sql-learn
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Schema         : hexagon

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 02/06/2018 10:50:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for jpa_administrator
-- ----------------------------
CREATE TABLE `jpa_administrator` (
  `id` bigint(20) NOT NULL,
  `created` bigint(20) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `mobile_phone` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `pwd` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `state` int(11) NOT NULL,
  `updated` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for jpa_feed_back_entity
-- ----------------------------
CREATE TABLE `jpa_feed_back_entity` (
  `id` bigint(20) NOT NULL,
  `app_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `created` bigint(20) DEFAULT NULL,
  `error_api` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `error_desc` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `error_request` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `error_request_time` bigint(20) DEFAULT NULL,
  `error_response` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `error_type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `page_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `referer` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `state` int(11) NOT NULL,
  `updated` bigint(20) DEFAULT NULL,
  `user_agent` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

SET FOREIGN_KEY_CHECKS = 1;
