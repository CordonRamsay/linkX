-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: linkx_db
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `board_dept_tbl`
--

DROP TABLE IF EXISTS `board_dept_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_dept_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` varchar(3000) NOT NULL,
  `createId` bigint NOT NULL,
  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `viewQty` int NOT NULL DEFAULT '0',
  `likeQty` int NOT NULL DEFAULT '0',
  `updateDt` varchar(20) DEFAULT NULL,
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  `majorId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  KEY `deptId` (`majorId`),
  CONSTRAINT `board_dept_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`),
  CONSTRAINT `board_dept_tbl_ibfk_2` FOREIGN KEY (`majorId`) REFERENCES `major_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_dept_tbl`
--

LOCK TABLES `board_dept_tbl` WRITE;
/*!40000 ALTER TABLE `board_dept_tbl` DISABLE KEYS */;
INSERT INTO `board_dept_tbl` VALUES (1,'asdasd','asdsad',1,'2024-10-19 00:50:10',0,0,NULL,0,17),(2,'dasdasdsa','dasdsadsa',1,'2024-10-19 00:50:49',0,0,NULL,0,3),(3,'asdsadas','asddsa',1,'2024-10-19 01:05:41',7,4,NULL,0,2),(4,'asdsad','asd',1,'2024-10-19 01:10:35',1,1,NULL,0,4),(5,'컴퓨터보안','ㅁㄴㅇ',1,'2024-10-19 01:10:44',11,4,NULL,0,2),(6,'전공','ㅁㄴㅇ',1,'2024-10-19 01:11:11',0,0,NULL,0,3),(7,' ffbfv','fvxcvxc',2,'2024-10-30 19:17:58',0,0,NULL,0,9),(8,'dasdasdas','dasdasd',2,'2024-11-12 01:39:05',0,0,NULL,1,2),(9,'asdsad','ㅁㅁ',2,'2024-11-12 01:39:13',0,1,'2024-11-13 00:46:27',0,1),(10,'ㅇㄴㅁㅇㄴㅁ','<p style=\"text-align: right; line-height: 1.5;\">ㅇㅁㄴ</p><table class=\"table table-bordered\"><tbody><tr><td>ㅁㄴㅇ</td><td>ㅁㄴㅇㄴㅁ</td></tr><tr><td>ㅁㄴㅇㄴㅁㅇ</td><td>ㅇㅁㄴㅇㄴㅁ</td></tr><tr><td>ㅁㄴㅇㅁㄴㅇ</td><td>ㅇㄴㅁㅇㄴㅁ</td></tr></tbody></table><p style=\"text-align: right; line-height: 1.5;\"><br></p><p style=\"text-align: right; line-height: 1.5;\">ㅁㄴㅇㄴㅁ</p><ol><li style=\"text-align: right; \"><br></li><li><ol><li>	                    </li></ol></li></ol>,<p style=\"text-align: right; line-height: 1.5;\">ㅇㅁㄴ</p><table class=\"table table-bordered\"><tbody><tr><td>ㅁㄴㅇ</td><td>ㅁㄴㅇㄴㅁ</td></tr><tr><td>ㅁㄴㅇㄴㅁㅇ</td><td>ㅇㅁㄴㅇㄴㅁ</td></tr><tr><td>ㅁㄴㅇㅁㄴㅇ</td><td>ㅇㄴㅁㅇㄴㅁ</td></tr></tbody></table><p style=\"text-align: right; line-height: 1.5;\"><br></p><p style=\"text-align: right; line-height: 1.5;\">ㅁㄴㅇㄴㅁ</p><ol><li style=\"text-align: right; \"><br></li><li><ol><li>	                    </li></ol></li></ol>',3,'2024-11-19 01:42:01',0,1,NULL,0,1),(11,'xx','xx,xx',3,'2024-11-25 00:03:19',0,1,NULL,0,1),(12,'dasasd','asddsa,asddsa',3,'2024-11-25 00:03:24',0,1,NULL,0,1),(13,'adsasd','asddas,asddas',3,'2024-11-25 00:03:44',0,1,NULL,0,1);
/*!40000 ALTER TABLE `board_dept_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_free_tbl`
--

DROP TABLE IF EXISTS `board_free_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_free_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `createId` bigint NOT NULL,
  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `viewQty` int NOT NULL DEFAULT '0',
  `likeQty` int NOT NULL DEFAULT '0',
  `updateDt` varchar(20) DEFAULT NULL,
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  CONSTRAINT `board_free_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_free_tbl`
--

LOCK TABLES `board_free_tbl` WRITE;
/*!40000 ALTER TABLE `board_free_tbl` DISABLE KEYS */;
INSERT INTO `board_free_tbl` VALUES (2,'sadasd','수정했어요',1,'2024-10-19 00:47:15',21,1,'2024-11-13 00:45:28',1),(3,'qwdqwdwqd','qwdqwd',1,'2024-10-19 00:47:42',5,0,NULL,1),(4,'asdasdas','aa',2,'2024-11-06 10:05:28',0,0,'2024-11-12 02:35:35',1),(5,'ㅁㄴㅇㅁㄴ','ㅇㄴㅁㅁㄴㅇ',2,'2024-11-13 00:47:03',0,1,'2024-11-13 00:47:16',1),(6,'ㅁㄴㅇㄴㅁㅇ','ㄴㅁㅇㄴㅁㅇ',2,'2024-11-13 00:48:51',0,0,NULL,1),(7,'ㅇㅁㄴㄴㅇ','ㅇㄴㅁㅇㅁㄴㅇㄴㅁㅇ',2,'2024-11-13 00:49:52',0,0,NULL,1),(8,'ㅁㄴㅇㄴㅇㅁ','ㅇㅁㄴㅇㅁㄴㅇ',2,'2024-11-13 00:50:37',0,0,NULL,1),(9,'ㅁㅇㅁㄴㅇㄴㅁㅇ','ㅁㄴㅇㅇㄴㅁㅇㅁㄴ',2,'2024-11-13 00:54:56',0,0,NULL,1),(10,'ㅁㄴㅇㅁㄴㅇ','ㄴㅇㅁㅇㅁㄴ',2,'2024-11-13 01:11:20',0,0,NULL,1),(11,'adsdsa','dsa',2,'2024-11-14 12:51:28',10,1,NULL,1),(12,'asdsadads','수정했어요',3,'2024-11-19 01:36:09',3,2,'2024-11-19 01:41:05',1),(13,'ㅁㅇㄴㅁ','ㅁㄴㅇ<table class=\"table table-bordered\"><tbody><tr><td>ㅁㄴㅇ</td><td><br></td></tr><tr><td>ㅁㄴㅇ</td><td><br></td></tr><tr><td>ㄴㅁㅇ</td><td><br></td></tr><tr><td>ㅁㅇㄴㄴㅁㅇ</td><td><br></td></tr><tr><td>ㅇㄴㅁ</td><td><br></td></tr></tbody></table>,ㅁㄴㅇ<table class=\"table table-bordered\"><tbody><tr><td>ㅁㄴㅇ</td><td><br></td></tr><tr><td>ㅁㄴㅇ</td><td><br></td></tr><tr><td>ㄴㅁㅇ</td><td><br></td></tr><tr><td>ㅁㅇㄴㄴㅁㅇ</td><td><br></td></tr><tr><td>ㅇㄴㅁ</td><td><br></td></tr></tbody></table>',3,'2024-11-19 01:42:36',1,1,NULL,0),(14,'asdsada','sdsaasd,sdsaasd',3,'2024-11-19 01:54:49',1,1,NULL,0),(15,'ㅁㄴㅇㅁㄴㅇ','<span style=\"font-size: 96px;\">ㅊㅌㅋㅊㅋㅌㅊㅌㅋㅊㅌㅋㅊㅋㅌ</span>,<span style=\"font-size: 96px;\">ㅊㅌㅋㅊㅋㅌㅊㅌㅋㅊㅌㅋㅊㅋㅌ</span>',2,'2024-11-20 11:08:05',207,1,NULL,0),(16,'ㅁㅁㅁ','ㅁㅁㅁㅁ',3,'2024-11-28 01:45:20',0,0,NULL,0);
/*!40000 ALTER TABLE `board_free_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board_like_tbl`
--

DROP TABLE IF EXISTS `board_like_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board_like_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `boardType` varchar(20) NOT NULL,
  `boardId` bigint NOT NULL,
  `createId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  CONSTRAINT `board_like_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_like_tbl`
--

LOCK TABLES `board_like_tbl` WRITE;
/*!40000 ALTER TABLE `board_like_tbl` DISABLE KEYS */;
INSERT INTO `board_like_tbl` VALUES (37,'dept',3,2),(43,'dept',5,2),(44,'dept',4,2),(45,'dept',9,2),(46,'free',2,2),(52,'free',5,2),(57,'free',11,3),(62,'free',12,3),(63,'free',14,3),(65,'free',12,2),(81,'dept',10,3),(97,'free',15,3),(99,'free',13,3),(100,'dept',11,3),(104,'dept',13,3),(105,'dept',12,3);
/*!40000 ALTER TABLE `board_like_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_like_tbl`
--

DROP TABLE IF EXISTS `comment_like_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_like_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `commentId` bigint NOT NULL,
  `createId` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  KEY `commentId` (`commentId`),
  CONSTRAINT `comment_like_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`),
  CONSTRAINT `comment_like_tbl_ibfk_2` FOREIGN KEY (`commentId`) REFERENCES `comment_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=261 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like_tbl`
--

LOCK TABLES `comment_like_tbl` WRITE;
/*!40000 ALTER TABLE `comment_like_tbl` DISABLE KEYS */;
INSERT INTO `comment_like_tbl` VALUES (198,31,3),(202,30,3),(238,8,3),(245,32,3),(247,25,3),(249,23,3),(250,24,3),(251,19,3),(255,29,3),(256,28,3),(259,38,3);
/*!40000 ALTER TABLE `comment_like_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment_tbl`
--

DROP TABLE IF EXISTS `comment_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comment_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `boardType` varchar(20) NOT NULL,
  `boardId` bigint NOT NULL,
  `createId` bigint NOT NULL,
  `comment` varchar(1000) NOT NULL,
  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `likeQty` int NOT NULL DEFAULT '0',
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  `updateDt` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  CONSTRAINT `comment_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_tbl`
--

LOCK TABLES `comment_tbl` WRITE;
/*!40000 ALTER TABLE `comment_tbl` DISABLE KEYS */;
INSERT INTO `comment_tbl` VALUES (1,'free',4,2,'ads','2024-11-12 00:52:17',0,0,NULL),(2,'free',11,2,'asddsa','2024-11-14 12:51:34',0,0,NULL),(3,'free',11,2,'asdasdasd','2024-11-14 12:52:54',0,0,NULL),(4,'free',11,2,'asdsadasd','2024-11-14 12:53:01',0,0,NULL),(5,'free',11,3,'asdasd','2024-11-19 01:35:03',0,0,NULL),(6,'free',12,3,'d','2024-11-19 01:37:14',0,0,NULL),(7,'free',13,3,'ㅇㅁㄴㅇㄴㅁ','2024-11-19 01:42:54',17,0,NULL),(8,'free',13,3,'ㅇㄴㅁㄴㅇㅁ','2024-11-19 01:42:57',33,0,NULL),(9,'free',14,3,'asdsad','2024-11-19 01:55:28',0,1,NULL),(10,'free',14,3,'dasdsa','2024-11-19 02:01:51',0,1,NULL),(11,'free',14,3,'asdasdasd','2024-11-19 02:02:00',0,1,NULL),(12,'free',14,3,'sdasddsad','2024-11-19 02:02:01',0,1,NULL),(13,'free',14,3,'가\n','2024-11-19 02:02:06',0,1,NULL),(14,'free',14,3,'dadsadsadsadsa','2024-11-19 02:02:08',0,1,'2024-11-19 03:11:39'),(15,'free',14,3,'asdasdasdasdsadasdsadasdasdasd','2024-11-19 03:05:47',0,0,'2024-11-20 10:07:58'),(16,'free',14,3,'asdasdasdasdsadasdsad','2024-11-19 03:14:08',0,1,'2024-11-20 10:07:54'),(17,'free',15,2,'ㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅇㅁㄴㅁㄴ','2024-11-20 11:12:39',0,1,'2024-11-20 11:13:04'),(18,'free',15,2,'ㅇㅁㄴㅇㄴㅁ','2024-11-20 11:13:47',0,0,NULL),(19,'free',15,3,'ㅇㅁㄴㅇㅁ','2024-11-20 12:08:02',1,1,NULL),(20,'free',15,3,'ㅇㅁㄴㅇㄴㅁㅇ','2024-11-20 12:12:10',0,1,NULL),(21,'free',15,3,'ㅁㄴㅇㅁㄴㅇ','2024-11-20 12:13:41',0,1,NULL),(22,'free',15,2,'dasdsad','2024-11-20 12:14:27',0,0,NULL),(23,'free',15,2,'asdsad','2024-11-20 12:14:29',1,0,NULL),(24,'free',15,3,'asdsad','2024-11-22 11:45:47',1,1,'2024-11-22 11:45:54'),(25,'free',15,3,'asdsadsad','2024-11-22 12:23:25',1,1,'2024-11-22 12:37:06'),(26,'dept',10,3,'asddsaa','2024-11-24 12:59:28',0,0,NULL),(27,'free',14,3,'dasda','2024-11-24 13:57:51',0,0,NULL),(28,'free',14,3,'dasdsa','2024-11-24 13:57:54',1,0,NULL),(29,'free',14,3,'ㅇㅁㄴㅁㅇㄴ','2024-11-24 14:04:20',1,0,NULL),(30,'free',15,3,'ㅁㅇㅁㄴㅇ','2024-11-24 14:06:45',1,1,NULL),(31,'free',15,3,'ㅇㅁㄴㅇㅁㄴㅇㅁㄴ','2024-11-24 14:06:48',2,1,NULL),(32,'free',15,3,'dsadas','2024-11-24 16:23:18',2,1,NULL),(33,'dept',10,3,'adasda','2024-11-25 00:05:40',0,0,NULL),(34,'dept',11,3,'dasdsa','2024-11-25 00:25:05',0,0,NULL),(35,'dept',13,3,'adsasd','2024-11-25 00:30:11',0,1,NULL),(36,'dept',13,3,'ㅊㅋㅌㅊㅋ','2024-11-25 00:32:47',0,0,'2024-11-25 00:32:49'),(37,'dept',12,3,'dasdsa','2024-11-25 00:37:39',0,0,NULL),(38,'dept',12,3,'dassda','2024-11-25 00:37:41',1,0,NULL);
/*!40000 ALTER TABLE `comment_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `major_tbl`
--

DROP TABLE IF EXISTS `major_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `major_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `majorName` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `major_tbl`
--

LOCK TABLES `major_tbl` WRITE;
/*!40000 ALTER TABLE `major_tbl` DISABLE KEYS */;
INSERT INTO `major_tbl` VALUES (1,'컴퓨터공학과'),(2,'컴퓨터보안공학과'),(3,'전자공학과'),(4,'정보통신공학과'),(5,'기계공학과'),(6,'산업경영공학과'),(7,'전기공학과'),(8,'토목공학과'),(9,'지적공간정보학과'),(10,'드론정보공학과'),(11,'경영학과'),(12,'세무회계과'),(13,'사회복지과'),(14,'부동산경영과'),(15,'항공서비스과'),(16,'일본어과'),(17,'유아교육과'),(18,'문예창작과'),(19,'중국어비즈니스과'),(20,'산업디자인과'),(21,'커뮤니케이션디자인과'),(22,'패션리빙디자인과'),(23,'사회체육과'),(24,'뷰티매니지먼트과'),(25,'보건의료정보과'),(26,'실용음악과'),(27,'연극영상학과');
/*!40000 ALTER TABLE `major_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `petition_tbl`
--

DROP TABLE IF EXISTS `petition_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `petition_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `petiTitle` varchar(100) NOT NULL,
  `petiField` varchar(100) NOT NULL,
  `petiContent` varchar(3000) NOT NULL,
  `userId` bigint NOT NULL,
  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `endDt` datetime DEFAULT NULL,
  `agreeQty` int NOT NULL DEFAULT '0',
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  `playing` tinyint NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `petition_tbl_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petition_tbl`
--

LOCK TABLES `petition_tbl` WRITE;
/*!40000 ALTER TABLE `petition_tbl` DISABLE KEYS */;
INSERT INTO `petition_tbl` VALUES (44,'adsasdasd','강의','asdasdas',3,'2024-11-28 12:24:05','2024-12-05 12:24:05',1,0,1),(45,'asdasd','유지보수','asdasdsad',3,'2024-11-28 12:24:40','2024-12-05 12:24:40',1,0,1),(46,'asdasd','유지보수','asdsad',6,'2024-11-28 12:35:13','2024-12-05 12:35:13',0,0,1),(47,'bb','유지보수','bb',6,'2024-11-28 12:51:22','2024-12-05 12:51:22',0,0,1);
/*!40000 ALTER TABLE `petition_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_tbl`
--

DROP TABLE IF EXISTS `review_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reviewTitle` varchar(50) NOT NULL,
  `reviewContent` varchar(300) NOT NULL,
  `restId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `reviewDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `reviewStar` float DEFAULT NULL,
  `reviewLike` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `restId` (`restId`),
  CONSTRAINT `review_tbl_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_tbl`
--

LOCK TABLES `review_tbl` WRITE;
/*!40000 ALTER TABLE `review_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `review_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `signatures_tbl`
--

DROP TABLE IF EXISTS `signatures_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `signatures_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` bigint NOT NULL,
  `petiId` bigint NOT NULL,
  `sigDt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `petiId` (`petiId`),
  CONSTRAINT `signatures_tbl_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_tbl` (`id`),
  CONSTRAINT `signatures_tbl_ibfk_2` FOREIGN KEY (`petiId`) REFERENCES `petition_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signatures_tbl`
--

LOCK TABLES `signatures_tbl` WRITE;
/*!40000 ALTER TABLE `signatures_tbl` DISABLE KEYS */;
INSERT INTO `signatures_tbl` VALUES (20,3,44,'2024-11-28 12:32:24'),(21,3,45,'2024-11-28 12:32:43');
/*!40000 ALTER TABLE `signatures_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taste_rest_tbl`
--

DROP TABLE IF EXISTS `taste_rest_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taste_rest_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `link` varchar(200) NOT NULL,
  `category` varchar(50) NOT NULL,
  `addr` varchar(100) NOT NULL,
  `roadaddr` varchar(100) NOT NULL,
  `mapx` varchar(30) NOT NULL,
  `mapy` varchar(30) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taste_rest_tbl`
--

LOCK TABLES `taste_rest_tbl` WRITE;
/*!40000 ALTER TABLE `taste_rest_tbl` DISABLE KEYS */;
INSERT INTO `taste_rest_tbl` VALUES (4,'팔공티 <b>명지전문대</b>점','http://www.palgongtea.co.kr/','카페,디저트>차','서울특별시 서대문구 홍은동 367','서울특별시 서대문구 가좌로 118 103호','1269257078','375830049'),(5,'메가MGC커피 <b>명지전문대</b>점','','카페,디저트>카페','서울특별시 서대문구 홍은동 342-16 1층','서울특별시 서대문구 가좌로 146 1층','1269237572','375850014'),(6,'김밥천국 <b>명지전문대</b>점','','음식점>분식','서울특별시 서대문구 홍은동 347','서울특별시 서대문구 가좌로 140','1269241894','375845316'),(7,'파리바게뜨 충암점','http://www.paris.co.kr/','카페,디저트>베이커리','서울특별시 서대문구 홍은동 342-2','서울특별시 서대문구 가좌로 144 (홍은동)','1269238993','375848465'),(8,'봉구스밥버거 <b>명지전문대</b>점','http://bongousse.net/','음식점>분식','서울특별시 서대문구 홍은동 355','서울특별시 서대문구 가좌로 131','1269242916','375839296'),(49,'봉일천 장군집','','한식>돼지고기구이','서울특별시 서대문구 북가좌2동 335-17','서울특별시 서대문구 응암로 141','1269146230','375849680'),(50,'정거장뒤','https://www.instagram.com/busto0op','음식점>퓨전음식','서울특별시 은평구 응암동 749-61 우측','서울특별시 은평구 응암로12길 21 우측','1269191604','375874432'),(51,'작은스페인','https://app.catchtable.co.kr/ct/shop/little.spain.seoul','음식점>스페인음식','서울특별시 서대문구 연희동 81-27','서울특별시 서대문구 연희로25길 37','1269340196','375717835'),(52,'가타쯔무리','http://facebook.com/katatsumuriudong','음식점>일식>우동,소바','서울특별시 서대문구 남가좌동 8-25','서울특별시 서대문구 명지대길 72','1269234861','375832536'),(53,'끝집 가재울본점','http://instagram.com/lastzip','술집>요리주점','서울특별시 서대문구 남가좌동 385 201동 108호','서울특별시 서대문구 가재울미래로 2 201동 108호','1269169922','375749033'),(54,'제로백 피시방 <b>명지</b>대점','http://www.facebook.com/zero100.Myongji','스포츠,오락>PC방','서울특별시 서대문구 남가좌동 7-4','서울특별시 서대문구 명지대길 54 2층 제로백 PC방','1269238906','375824746'),(55,'에이플 PC CAFE <b>명지</b>대점','','스포츠,오락>PC방','서울특별시 서대문구 남가좌동 322-18 지하','서울특별시 서대문구 명지대1길 2 지하','1269247037','375812775'),(56,'긱스타<b>pc방</b>','','스포츠,오락>PC방','서울특별시 서대문구 홍은동 401-40 3층','서울특별시 서대문구 거북골로 10 3층','1269258987','375810879'),(57,'쓰리팝<b>PC방</b>','','스포츠,오락>PC방','서울특별시 서대문구 홍은동 401-40 2층','서울특별시 서대문구 거북골로 10 2층','1269259133','375810705'),(60,'색연필 <b>명지</b>점','','쇼핑,유통>문구,팬시용품','서울특별시 서대문구 홍은동 356-39','서울특별시 서대문구 가좌로 120','1269254700','375832360'),(61,'현대문구사','','쇼핑,유통>문구,팬시용품','서울특별시 서대문구 홍은동 361-30','서울특별시 서대문구 명지2길 56','1269275541','375842386'),(62,'afz문구','','쇼핑,유통>문구,팬시용품','서울특별시 서대문구 홍은동 401-15 지하 1층','서울특별시 서대문구 명지대길 10 지하 1층','1269250905','375808478'),(63,'에이프럼지 <b>명지</b>점','','쇼핑,유통>문구,팬시용품','서울특별시 서대문구 홍은동 401-15','서울특별시 서대문구 명지대길 10','1269251030','375807857'),(64,'하나<b>은행</b> <b>명지전문</b>대출장소','https://m.kebhana.com/br.html?br=1505','금융,보험>은행','서울특별시 서대문구 홍은동 323-15 사회교육관 1층','서울특별시 서대문구 가좌로 134 사회교육관 1층','1269258108','375845151'),(65,'하나<b>은행</b>365 <b>명지전문</b>대 본관','','금융,보험>은행','서울특별시 서대문구 홍은동 356-1 명지전문대학','서울특별시 서대문구 가좌로 134 명지전문대학','1269248910','375847640'),(66,'하나<b>은행</b>365 <b>명지전문</b>대 ATM','','금융,보험>은행','서울특별시 서대문구 홍은동 356-1','서울특별시 서대문구 가좌로 134 명지전문대학 사회교육관1층 (홍은동)','1269245494','375842746'),(67,'하나<b>은행</b> <b>명지전문</b>대 ATM','','금융,보험>은행','서울특별시 서대문구 홍은동 356-1','서울특별시 서대문구 가좌로 134 (홍은동)','1269245494','375842746'),(68,'하나<b>은행</b>365 <b>명지전문</b>대 ATM','','금융,보험>은행','서울특별시 서대문구 홍은동 356-1','서울특별시 서대문구 가좌로 134 명지전문대학 (홍은동)','1269245494','375842746');
/*!40000 ALTER TABLE `taste_rest_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `taste_review_tbl`
--

DROP TABLE IF EXISTS `taste_review_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `taste_review_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `reviewTitle` varchar(50) NOT NULL,
  `reviewContent` varchar(300) NOT NULL,
  `restId` bigint NOT NULL,
  `userId` bigint NOT NULL,
  `reviewDate` datetime DEFAULT CURRENT_TIMESTAMP,
  `reviewStar` float DEFAULT '0',
  `reviewLike` int NOT NULL DEFAULT '0',
  `userNickName` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  KEY `restId` (`restId`),
  CONSTRAINT `taste_review_tbl_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_tbl` (`id`),
  CONSTRAINT `taste_review_tbl_taste_rest_tbl_FK` FOREIGN KEY (`restId`) REFERENCES `taste_rest_tbl` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taste_review_tbl`
--

LOCK TABLES `taste_review_tbl` WRITE;
/*!40000 ALTER TABLE `taste_review_tbl` DISABLE KEYS */;
INSERT INTO `taste_review_tbl` VALUES (66,'asdasd','asdasdasdsasda',4,3,'2024-11-28 12:15:23',1,0,'eeeeeeee'),(67,'asdasdasdasdasd','as',5,3,'2024-11-28 12:15:32',1,0,'eeeeeeee');
/*!40000 ALTER TABLE `taste_review_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `loginId` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `phone` varchar(13) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `univ` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `majorId` bigint DEFAULT NULL,
  `stuNum` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role` varchar(10) NOT NULL DEFAULT 'USER',
  `active` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`loginId`),
  KEY `deptId` (`majorId`),
  CONSTRAINT `user_tbl_ibfk_1` FOREIGN KEY (`majorId`) REFERENCES `major_tbl` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
INSERT INTO `user_tbl` VALUES (1,'12312','123123123','123123','123123','123123',2,'123123','123123','123123','USER',0),(2,'qkrgusgh','$2a$10$DuH8cUyLWmGRp5px/FBXk.gb8fKJgh6r0tlFk4UwmPtnXB.2fGPi6','박현호','11111111','11111111',4,'2022151515','11111111','홍장군입니다','USER',0),(3,'eeeeeeee','$2a$10$W5cD2YY9jJoJRXC3rzAhx.NDqhdk9ncOCUgg2O25G62lcYIG/Mg0O','eeeeeeee','eeeeeeee','eeeeeeee',1,'eeeeeeee','eeeeeeee','eeeeeeee','USER',0),(4,'glaemfek','$2a$10$SYxinSMdimjZ3eb5VLNwROvub0z5imYNduF1tF/pLxEuPmt.TM5z.','박현호','01029910776','명지전문대학',10,'2022081044','phh1569@nate.com','aaa','USER',0),(5,'gusgh111','$2a$10$SlBwpneeHInSJC0qFC1SZemBnURZVhBwHjMfwHizphvKnot9Dc5Ra','박혀노','01029910776','명지전문대학',18,'2022081042','dsadsa@dasdas','홍장군이요','USER',0),(6,'aaaaaaaa','$2a$10$AS3B1L9du/i5oeGm1.SBY.gX9CiR/cWtw0lS.SrkGeg48o8QEFMYK','박현호','01022223333','명지전문대학',16,'2022081043','2222@2222','현호임당','USER',0);
/*!40000 ALTER TABLE `user_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'linkx_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 18:25:09
