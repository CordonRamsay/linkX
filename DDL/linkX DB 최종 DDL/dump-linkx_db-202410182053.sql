-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: linkx_db
-- ------------------------------------------------------
-- Server version	8.0.38

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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_dept_tbl`
--

LOCK TABLES `board_dept_tbl` WRITE;
/*!40000 ALTER TABLE `board_dept_tbl` DISABLE KEYS */;
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
  `content` varchar(3000) NOT NULL,
  `createId` bigint NOT NULL,
  `createDt` datetime DEFAULT CURRENT_TIMESTAMP,
  `viewQty` int NOT NULL DEFAULT '0',
  `likeQty` int NOT NULL DEFAULT '0',
  `updateDt` varchar(20) DEFAULT NULL,
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  CONSTRAINT `board_free_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_free_tbl`
--

LOCK TABLES `board_free_tbl` WRITE;
/*!40000 ALTER TABLE `board_free_tbl` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board_like_tbl`
--

LOCK TABLES `board_like_tbl` WRITE;
/*!40000 ALTER TABLE `board_like_tbl` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_like_tbl`
--

LOCK TABLES `comment_like_tbl` WRITE;
/*!40000 ALTER TABLE `comment_like_tbl` DISABLE KEYS */;
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
  PRIMARY KEY (`id`),
  KEY `createId` (`createId`),
  CONSTRAINT `comment_tbl_ibfk_1` FOREIGN KEY (`createId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment_tbl`
--

LOCK TABLES `comment_tbl` WRITE;
/*!40000 ALTER TABLE `comment_tbl` DISABLE KEYS */;
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
  `endDt` datetime NOT NULL,
  `agreeQty` int NOT NULL DEFAULT '0',
  `deleteYn` tinyint NOT NULL DEFAULT '0',
  `userNickname` varchar(100) NOT NULL,
  `playing` varchar(100) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`),
  CONSTRAINT `petition_tbl_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `user_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `petition_tbl`
--

LOCK TABLES `petition_tbl` WRITE;
/*!40000 ALTER TABLE `petition_tbl` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `signatures_tbl`
--

LOCK TABLES `signatures_tbl` WRITE;
/*!40000 ALTER TABLE `signatures_tbl` DISABLE KEYS */;
/*!40000 ALTER TABLE `signatures_tbl` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_tbl`
--

DROP TABLE IF EXISTS `user_tbl`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_tbl` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `userId` varchar(20) NOT NULL,
  `userPassword` varchar(20) NOT NULL,
  `userName` varchar(15) NOT NULL,
  `userPhone` varchar(13) NOT NULL,
  `userUniv` varchar(30) NOT NULL,
  `majorId` bigint DEFAULT NULL,
  `userNum` varchar(10) NOT NULL,
  `userEmail` varchar(50) NOT NULL,
  `userNickname` varchar(50) NOT NULL,
  `authority` tinyint NOT NULL DEFAULT '0',
  `active` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `userId` (`userId`),
  KEY `deptId` (`majorId`),
  CONSTRAINT `user_tbl_ibfk_1` FOREIGN KEY (`majorId`) REFERENCES `major_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_tbl`
--

LOCK TABLES `user_tbl` WRITE;
/*!40000 ALTER TABLE `user_tbl` DISABLE KEYS */;
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

-- Dump completed on 2024-10-18 20:53:03
