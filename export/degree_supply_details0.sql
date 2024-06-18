-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: degree
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `supply_details`
--

DROP TABLE IF EXISTS `supply_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply_details` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `price_per_amount` double NOT NULL,
  `medication_id` bigint DEFAULT NULL,
  `supply_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK52apfxh94lqrgw31737xmanc5` (`medication_id`),
  KEY `FKkshr61818ar64x455dr7ysi2j` (`supply_id`),
  CONSTRAINT `FK52apfxh94lqrgw31737xmanc5` FOREIGN KEY (`medication_id`) REFERENCES `medication` (`id`),
  CONSTRAINT `FKkshr61818ar64x455dr7ysi2j` FOREIGN KEY (`supply_id`) REFERENCES `supply` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply_details`
--

LOCK TABLES `supply_details` WRITE;
/*!40000 ALTER TABLE `supply_details` DISABLE KEYS */;
INSERT INTO `supply_details` VALUES (1,400,40,1,1),(2,300,20,2,1),(3,150,30,4,1),(4,10000,100,5,1);
/*!40000 ALTER TABLE `supply_details` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-17  9:30:23
