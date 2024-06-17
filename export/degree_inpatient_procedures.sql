CREATE DATABASE  IF NOT EXISTS `degree` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `degree`;
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
-- Table structure for table `inpatient_procedures`
--

DROP TABLE IF EXISTS `inpatient_procedures`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inpatient_procedures` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` int NOT NULL,
  `procedure_time` datetime(6) DEFAULT NULL,
  `inpatient_id` bigint DEFAULT NULL,
  `medication_storage_id` bigint DEFAULT NULL,
  `procedure_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhwt8bq5h62xytqb9btvymv0f` (`inpatient_id`),
  KEY `FK32g9kuvae0m34v26kdf62w24n` (`medication_storage_id`),
  KEY `FKalkasdvfpi5d8ks3yql79jvwp` (`procedure_id`),
  CONSTRAINT `FK32g9kuvae0m34v26kdf62w24n` FOREIGN KEY (`medication_storage_id`) REFERENCES `medication_storage` (`id`),
  CONSTRAINT `FKalkasdvfpi5d8ks3yql79jvwp` FOREIGN KEY (`procedure_id`) REFERENCES `procedures` (`id`),
  CONSTRAINT `FKhwt8bq5h62xytqb9btvymv0f` FOREIGN KEY (`inpatient_id`) REFERENCES `inpatient` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inpatient_procedures`
--

LOCK TABLES `inpatient_procedures` WRITE;
/*!40000 ALTER TABLE `inpatient_procedures` DISABLE KEYS */;
INSERT INTO `inpatient_procedures` VALUES (1,2,'2024-06-15 22:06:25.211881',1,2,2);
/*!40000 ALTER TABLE `inpatient_procedures` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-17  8:57:24
