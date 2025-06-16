-- MySQL dump 10.13  Distrib 8.2.0, for Linux (aarch64)
--
-- Host: localhost    Database: adminflow
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


--
-- Table structure for table `authorization`
--
CREATE DATABASE IF NOT EXISTS adminflow;

DROP TABLE IF EXISTS `authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authorization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(15) DEFAULT NULL,
  `describe` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorization`
--

LOCK TABLES `authorization` WRITE;
/*!40000 ALTER TABLE `authorization` DISABLE KEYS */;
INSERT INTO `authorization` VALUES (1,'add_user','添加用户'),(2,'update_user','修改用户'),(3,'delete_user','删除用户'),(4,'add_role','添加角色'),(5,'update_role','修改角色'),(6,'delete_role','删除角色'),(7,'delete_log','删除日志'),(8,'update_per','修改权限'),(9,'add_org','添加组织'),(10,'delete_org','删除组织'),(11,'update_org','修改组织'),(12,'update_menu','修改菜单'),(14,'export_data','导出数据');
/*!40000 ALTER TABLE `authorization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `menu` (
  `menu_id` int NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(10) DEFAULT NULL,
  `parent_id` int DEFAULT NULL,
  `path` varchar(25) DEFAULT NULL,
  `menu_role` varchar(10) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,'系统管理',0,'/system','一级菜单','Setting'),(2,'信息管理',0,'/system','一级菜单','Document'),(3,'日志管理',0,'/system','一级菜单','Notebook'),(4,'用户管理',1,'/system/user','二级菜单','UserFilled'),(5,'角色管理',1,'/system/role','二级菜单','Avatar'),(6,'菜单管理',1,'/system/menus','二级菜单','Menu'),(7,'部门管理',2,'/system/department','二级菜单','Tools'),(8,'员工管理',2,'/system/position','二级菜单',NULL),(9,'操作日志记录',3,'/system/operation','二级菜单',NULL),(10,'定时任务记录',3,'/system/scheduled','二级菜单',NULL),(11,'权限设置',1,'/system/permissions','二级菜单','StarFilled');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `send_id` int NOT NULL,
  `receive_id` int NOT NULL,
  `content` text,
  `time` varchar(20) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,1,2,'发送给nacos','2023-12-08',1),(2,2,1,'发送给breeze','2023-12-09',1),(3,1,2,'我回复你了','2023-12-09',1),(4,1,2,'nico你是傻逼','2023-12-09',0),(5,2,1,'你才是傻逼','2023-12-09',1),(6,1,2,'消息删除好使？','2023-12-09',1),(7,6,1,'你好，我是redis','2023-12-09',1),(8,1,6,'欢迎你，redis','2023-12-10',0),(9,2,1,'好使','2023-12-13',1),(10,1,2,'私信内容好使？','2023-12-13',1),(11,2,1,'我收到了','2023-12-13',1),(12,1,2,'好的','2023-12-13',0),(13,1,6,'cs','2023-12-14',0),(14,1,2,'真的好使','2023-12-17',0),(15,1,2,'收到，明白','2023-12-21',0),(16,1,2,'你吃晚饭了？','2023-12-24',0),(17,1,2,'2024年1月9日','2024-01-09',1),(18,1,2,'收到','2024-01-11',1),(19,1,2,'cnmlgb','2024-01-17',1),(20,1,2,'我是你爹','2024-01-17',1),(21,2,1,'你好？','2024-01-17',1),(22,1,2,'睡觉了','2024-01-17',1),(23,1,2,'你是傻逼','2024-01-18',1),(24,1,6,'测试','2024-02-02',0),(25,1,2,'你好','2024-02-02',1);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operation_data`
--

DROP TABLE IF EXISTS `operation_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `operation_data` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` varchar(15) DEFAULT NULL,
  `operation` varchar(150) NOT NULL,
  `date` varchar(20) NOT NULL,
  `type` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=438 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operation_data`
--

LOCK TABLES `operation_data` WRITE;
/*!40000 ALTER TABLE `operation_data` DISABLE KEYS */;
INSERT INTO `operation_data` VALUES (268,'breeze','breeze','2024-01-11 15:10:46','退出登录'),(269,'cs','cs登录;ip地址为127.0.0.1','2024-01-11 15:12:10','用户登录'),(270,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-11 16:47:02','用户登录'),(271,'breeze','breeze退出登录','2024-01-11 16:48:00','退出登录'),(272,'breeze','breeze登录;ip地址为0:0:0:0:0:0:0:1','2024-01-12 13:26:46','用户登录'),(273,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-12 14:32:15','用户登录'),(274,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 12]','2024-01-12 14:33:07','修改权限'),(275,'breeze','breeze退出登录','2024-01-12 16:23:18','退出登录'),(276,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 11:26:01','用户登录'),(277,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 12, 2]','2024-01-17 11:42:18','修改权限'),(278,'breeze','breeze退出登录','2024-01-17 14:06:38','退出登录'),(279,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 14:07:22','用户登录'),(280,'breeze','breeze退出登录','2024-01-17 14:11:10','退出登录'),(281,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 14:11:34','用户登录'),(282,'breeze','breeze退出登录','2024-01-17 15:18:36','退出登录'),(283,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 15:19:27','用户登录'),(284,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 2]','2024-01-17 15:21:49','修改权限'),(285,'breeze','breeze退出登录','2024-01-17 15:28:26','退出登录'),(286,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 15:31:30','用户登录'),(287,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 15:54:22','用户登录'),(288,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 2, 12]','2024-01-17 15:55:08','修改权限'),(289,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 16:22:46','用户登录'),(290,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 12]','2024-01-17 16:24:00','修改权限'),(291,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 16:49:57','用户登录'),(292,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 17:09:42','用户登录'),(293,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 4, 5, 6, 3, 14, 12, 2]','2024-01-17 17:17:01','修改权限'),(294,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 17:29:38','用户登录'),(295,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 18:54:46','用户登录'),(296,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 18:54:59','用户登录'),(297,'breeze','breeze退出登录','2024-01-17 19:02:45','退出登录'),(298,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 19:05:17','用户登录'),(299,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 19:36:07','用户登录'),(300,'breeze','breeze退出登录','2024-01-17 19:42:23','退出登录'),(301,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 19:43:37','用户登录'),(302,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 20:06:37','用户登录'),(303,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 20:26:34','用户登录'),(304,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 21:39:56','用户登录'),(305,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 21:55:45','用户登录'),(306,'breeze','breeze退出登录','2024-01-17 21:56:04','退出登录'),(307,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 21:56:16','用户登录'),(308,'breeze','breeze退出登录','2024-01-17 22:10:38','退出登录'),(309,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:11:25','用户登录'),(310,'breeze','breeze退出登录','2024-01-17 22:15:52','退出登录'),(311,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:16:28','用户登录'),(312,'breeze','breeze退出登录','2024-01-17 22:18:29','退出登录'),(313,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:19:16','用户登录'),(314,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:28:03','用户登录'),(315,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:29:23','用户登录'),(316,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:44:47','用户登录'),(317,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:44:56','用户登录'),(318,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:45:04','用户登录'),(319,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:45:15','用户登录'),(320,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:47:44','用户登录'),(321,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-17 22:49:10','用户登录'),(322,'nico','nico登录;ip地址为127.0.0.1','2024-01-17 22:53:18','用户登录'),(323,'breeze','breeze退出登录','2024-01-17 22:58:52','退出登录'),(324,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 10:23:25','用户登录'),(325,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 11:24:30','用户登录'),(326,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 11:43:35','用户登录'),(327,'breeze','breeze退出登录','2024-01-18 11:44:15','退出登录'),(328,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 11:48:50','用户登录'),(329,'breeze','breeze退出登录','2024-01-18 11:57:37','退出登录'),(330,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 12:01:19','用户登录'),(331,'breeze','breeze退出登录','2024-01-18 12:03:04','退出登录'),(332,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 12:04:25','用户登录'),(333,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 12:20:19','用户登录'),(334,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 12:36:49','用户登录'),(335,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 12:46:41','用户登录'),(336,NULL,'手机号为19972552055用户登录','2024-01-18 13:06:30','用户登录'),(337,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 13:11:14','用户登录'),(338,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 13:46:15','用户登录'),(339,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 14:11:57','用户登录'),(340,'breeze','breeze退出登录','2024-01-18 14:14:47','退出登录'),(341,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 14:15:05','用户登录'),(342,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 15:11:07','用户登录'),(343,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 15:36:12','用户登录'),(344,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 15:45:52','用户登录'),(345,'breeze','breeze退出登录','2024-01-18 15:46:04','退出登录'),(346,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 15:46:30','用户登录'),(347,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 15:46:40','用户登录'),(348,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 16:06:51','用户登录'),(349,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 16:34:46','用户登录'),(350,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:05:41','用户登录'),(351,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:18:30','用户登录'),(352,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:23:48','用户登录'),(353,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:27:03','用户登录'),(354,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:29:43','用户登录'),(355,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:30:52','用户登录'),(356,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:35:36','用户登录'),(357,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:37:51','用户登录'),(358,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:39:17','用户登录'),(359,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 17:56:47','用户登录'),(360,'breeze','breeze退出登录','2024-01-18 18:04:37','退出登录'),(361,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 18:10:05','用户登录'),(362,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 18:12:14','用户登录'),(363,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 18:13:49','用户登录'),(364,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 18:22:28','用户登录'),(365,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 18:47:09','用户登录'),(366,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:06:42','用户登录'),(367,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:13:53','用户登录'),(368,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:21:30','用户登录'),(369,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:29:48','用户登录'),(370,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:44:26','用户登录'),(371,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 19:56:44','用户登录'),(372,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 20:05:16','用户登录'),(373,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 20:14:00','用户登录'),(374,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-18 20:22:02','用户登录'),(375,'breeze','breeze登录;ip地址为0:0:0:0:0:0:0:1','2024-01-19 20:40:47','用户登录'),(376,'breeze','breeze退出登录','2024-01-19 20:43:28','退出登录'),(377,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-19 20:44:05','用户登录'),(378,'breeze','breeze退出登录','2024-01-19 20:49:21','退出登录'),(379,'breeze','breeze登录;ip地址为0:0:0:0:0:0:0:1','2024-01-19 20:51:39','用户登录'),(380,'breeze','breeze退出登录','2024-01-19 21:06:15','退出登录'),(381,'breeze','breeze登录;ip地址为0:0:0:0:0:0:0:1','2024-01-19 21:11:16','用户登录'),(382,'breeze','breeze退出登录','2024-01-19 21:11:35','退出登录'),(383,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-19 21:25:52','用户登录'),(384,'breeze','breeze退出登录','2024-01-19 21:44:25','退出登录'),(385,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 18:34:56','用户登录'),(386,'breeze','breeze退出登录','2024-01-20 18:35:40','退出登录'),(387,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 18:38:08','用户登录'),(388,'breeze','breeze退出登录','2024-01-20 18:43:51','退出登录'),(389,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 18:47:24','用户登录'),(390,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 19:14:20','用户登录'),(391,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 20:16:45','用户登录'),(392,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-20 20:45:54','用户登录'),(393,NULL,'添加运维部','2024-01-20 20:46:16','添加组织'),(394,NULL,'删除id为31的组织','2024-01-20 20:55:27','删除组织'),(395,NULL,'添加开发部门','2024-01-20 20:55:46','添加组织'),(396,'breeze','breeze退出登录','2024-01-20 21:08:48','退出登录'),(397,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-21 18:43:31','用户登录'),(398,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 5, 6, 3, 14, 12, 2]','2024-01-21 19:07:50','修改权限'),(399,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-21 19:11:29','用户登录'),(400,'breeze','breeze登录;ip地址为127.0.0.1','2024-01-21 19:44:54','用户登录'),(401,'breeze','breeze退出登录','2024-01-21 19:48:27','退出登录'),(402,'breeze','breeze登录;ip地址为127.0.0.1','2024-02-02 20:19:55','用户登录'),(403,'breeze','breeze登录;ip地址为127.0.0.1','2024-02-02 20:20:31','用户登录'),(404,NULL,'添加redis用户','2024-02-02 20:21:41','添加用户'),(405,NULL,'添加测试1','2024-02-02 20:23:11','添加组织'),(406,NULL,'添加测试2','2024-02-02 20:23:17','添加组织'),(407,NULL,'删除id为34的组织','2024-02-02 20:23:30','删除组织'),(408,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 6, 3, 14, 12, 2]','2024-02-02 20:23:57','修改权限'),(409,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 3, 14, 12, 2]','2024-02-02 20:24:06','修改权限'),(410,NULL,'修改id为1角色权限,新的权限id为[7, 9, 10, 8, 1, 3, 14, 12, 2, 5, 4, 6]','2024-02-02 20:24:15','修改权限'),(411,'nico','nico登录;ip地址为127.0.0.1','2024-02-02 20:25:46','用户登录'),(412,'nico','nico退出登录','2024-02-02 20:27:05','退出登录'),(413,'breeze','breeze退出登录','2024-02-02 20:27:14','退出登录'),(414,NULL,'手机号为19972552055用户登录','2024-02-15 19:39:30','用户登录'),(415,'breeze','breeze登录;ip地址为127.0.0.1','2024-02-15 19:41:30','用户登录'),(416,'breeze','breeze退出登录','2024-02-15 19:44:00','退出登录'),(417,NULL,'手机号为19972552055用户登录','2024-02-15 19:48:52','用户登录'),(418,NULL,'手机号为19972552055用户登录','2024-02-15 19:53:44','用户登录'),(419,NULL,'手机号为19972552055用户登录','2024-02-15 20:00:48','用户登录'),(420,NULL,'手机号为19972552055用户登录','2024-02-15 20:02:51','用户登录'),(421,'breeze','breeze退出登录','2024-02-15 20:10:50','退出登录'),(422,'breeze','breeze登录;ip地址为127.0.0.1','2024-02-15 20:51:24','用户登录'),(423,'breeze','breeze退出登录','2024-02-15 20:52:13','退出登录'),(424,'breeze','breeze登录;ip地址为127.0.0.1','2024-03-04 14:23:19','用户登录'),(425,'breeze','breeze退出登录','2024-03-04 14:24:44','退出登录'),(426,'breeze','breeze登录;ip地址为127.0.0.1','2024-03-04 14:51:16','用户登录'),(427,'breeze','breeze退出登录','2024-03-04 14:52:29','退出登录'),(428,'breeze','breeze登录;ip地址为127.0.0.1','2024-03-12 19:36:15','用户登录'),(429,'nico','nico登录;ip地址为127.0.0.1','2024-03-12 19:37:51','用户登录'),(430,'breeze','breeze退出登录','2024-03-12 19:38:08','退出登录'),(431,'breeze','breeze登录;ip地址为127.0.0.1','2024-03-12 19:38:39','用户登录'),(432,'breeze','breeze退出登录','2024-03-12 19:39:30','退出登录'),(433,'nico','nico退出登录','2024-03-12 19:39:35','退出登录'),(434,'breeze','breeze登录;ip地址为0:0:0:0:0:0:0:1','2024-03-13 09:26:34','用户登录'),(435,'breeze','breeze退出登录','2024-03-13 09:35:12','退出登录'),(436,'breeze','breeze登录;ip地址为127.0.0.1','2024-03-18 10:05:10','用户登录'),(437,'breeze','breeze退出登录','2024-03-18 10:19:54','退出登录');
/*!40000 ALTER TABLE `operation_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization`
--

DROP TABLE IF EXISTS `organization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `organization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `parentId` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization`
--

LOCK TABLES `organization` WRITE;
/*!40000 ALTER TABLE `organization` DISABLE KEYS */;
INSERT INTO `organization` VALUES (1,'总部',-1),(2,'财务部',1),(12,'人力资源部',1),(22,'市场部',1),(28,'技术部',1),(29,'研发部',28),(30,'测试部',28),(32,'开发部',29),(33,'测试1',30);
/*!40000 ALTER TABLE `organization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(30) NOT NULL,
  `nameZh` varchar(30) NOT NULL,
  `create_time` varchar(20) DEFAULT NULL,
  `description` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'role_admin','管理员','2024-01-10','拥有所有权限'),(2,'role_guest','开发者','2024-01-08','拥有部分权限'),(3,'role_travelers','游客','2024-01-09','少部分角色');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_authorization`
--

DROP TABLE IF EXISTS `role_authorization`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_authorization` (
  `id` int NOT NULL AUTO_INCREMENT,
  `rId` int NOT NULL,
  `aId` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=492 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_authorization`
--

LOCK TABLES `role_authorization` WRITE;
/*!40000 ALTER TABLE `role_authorization` DISABLE KEYS */;
INSERT INTO `role_authorization` VALUES (42,10,7),(59,12,7),(92,2,9),(93,2,3),(94,2,4),(95,2,10),(96,2,6),(97,2,1),(98,2,12),(100,3,7),(101,3,12),(480,1,7),(481,1,9),(482,1,10),(483,1,8),(484,1,1),(485,1,3),(486,1,14),(487,1,12),(488,1,2),(489,1,5),(490,1,4),(491,1,6);
/*!40000 ALTER TABLE `role_authorization` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menu`
--

DROP TABLE IF EXISTS `role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_menu` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mId` int DEFAULT NULL,
  `rId` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=423 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menu`
--

LOCK TABLES `role_menu` WRITE;
/*!40000 ALTER TABLE `role_menu` DISABLE KEYS */;
INSERT INTO `role_menu` VALUES (121,11,3),(122,7,3),(123,9,3),(124,10,3),(381,4,2),(382,11,2),(383,7,2),(384,9,2),(385,10,2),(415,4,1),(416,5,1),(417,6,1),(418,11,1),(419,7,1),(420,8,1),(421,9,1),(422,10,1);
/*!40000 ALTER TABLE `role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `phone` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `userFace` varchar(500) DEFAULT NULL,
  `embod` varchar(200) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `state` tinyint(1) NOT NULL,
  `organizationId` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,19972552055,'中国武汉','breeze','20011203e','https://picx.zhimg.com/v2-02f89d05a781ffed9fd2e32654d93135_720w.jpg?source=172ae18b','都是风景，幸会','3548297839@qq.com',1,1),(2,19972551955,'中国深圳','nico','1234','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQCA6Y2066ZhqiBCDTNwwmpQPzPp5nKshTsJg&usqp=CAU','花店不开了','12345@qq.com',1,2),(6,12343435,'中国北京','redis','123456','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhqsg6taOcgqTcBPTX_woAGwaGmapUKWFgFg&usqp=CAU','凡是过往，皆为序章','19972552055@qq.com',1,12),(22,47548934,'中国青岛','dubbo','123','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhqsg6taOcgqTcBPTX_woAGwaGmapUKWFgFg&usqp=CAU','花自向阳开，我终向往你','354829783@qq.com',1,22),(44,19888333344,'中国武汉','redis','cvcvcv',NULL,'cd','3548297839@qq.com',1,30);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_message`
--

DROP TABLE IF EXISTS `user_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `uId` int NOT NULL,
  `mId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_message`
--

LOCK TABLES `user_message` WRITE;
/*!40000 ALTER TABLE `user_message` DISABLE KEYS */;
INSERT INTO `user_message` VALUES (1,1,'null,1,2,3,4,5,11,13,14,10,12,8,16,18,19,20,15,22,23,7,6,9'),(2,2,'6,5,9,4,2,18,16,14,11,3,20,25,23,12');
/*!40000 ALTER TABLE `user_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1),(2,2,2),(3,3,3),(6,6,3),(7,9,3),(8,22,2),(10,25,3),(11,26,2),(12,27,2),(13,28,2),(14,30,2),(15,31,2),(25,44,3);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-03-29 15:19:50
