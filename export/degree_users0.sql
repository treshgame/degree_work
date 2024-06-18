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
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `enabled` bit(1) NOT NULL,
  `password` varchar(100) NOT NULL,
  `username` varchar(50) NOT NULL,
  `employee_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_r43af9ap4edm43mmtq01oddj6` (`username`),
  UNIQUE KEY `UK_d1s31g1a7ilra77m65xmka3ei` (`employee_id`),
  CONSTRAINT `FK6p2ib82uai0pj9yk1iassppgq` FOREIGN KEY (`employee_id`) REFERENCES `employees` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '','$2a$10$ZQWGg26M8FSrSGggiSZ.BOgBFMqI/SNXbibbgsvaKWD2Gj.fPPx/y','owner',NULL),(6,_binary '','$2a$10$9PQIcgabWKaL8anehlxpZOeKAyhlAgYwjWw/J3ATgxJ5bd/Iv8FDS','ivan',4),(7,_binary '','$2a$10$BbnjfHGieH7S9eX94ETsTeo5vLetyaPeB3vm9e7NyJWVoBEWPFu7W','SAM3',7),(8,_binary '','$2a$10$MQLRXI/tSMEiSxfAu7C/lu8MgQngg8KkWyIJS/o0YS0P0IxJvowVa','VvvNi',8),(9,_binary '','$2a$10$SXbBzdPx6S6IuWsh/BhBV.9OxJF.lTAr5.7dtP73A10hGDwgeGvFa','Vise',9),(10,_binary '','$2a$10$lQRkeu/774EpscOA8v.Yr.Iu20fMHDPJXRRWUdug2u7xvPsqA99Cy','Alex',10),(11,_binary '','$2a$10$eDm2J/k9rOLB/MXdHi8.BOq91niQYN6L9XiWmrJ4FR881jbJJ1aH.','Damir',11);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-17  9:30:22
