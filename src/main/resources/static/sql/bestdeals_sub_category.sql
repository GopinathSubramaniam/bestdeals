-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: 93.127.226.135    Database: bestdeals
-- ------------------------------------------------------
-- Server version	5.1.73

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `sub_category`
--

DROP TABLE IF EXISTS `sub_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sub_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `step_id` int(11) DEFAULT NULL,
  `category_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKl65dyy5me2ypoyj8ou1hnt64e` (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_category`
--

LOCK TABLES `sub_category` WRITE;
/*!40000 ALTER TABLE `sub_category` DISABLE KEYS */;
INSERT INTO `sub_category` VALUES (1,'test resto','Restaurant',NULL,1),(2,'Bar description','Bar',NULL,1),(3,'software description','Software',NULL,2),(4,'Rice','Rice',NULL,1),(8,'','Redimade and cloth',NULL,8),(7,'Test','Demo3',NULL,5),(9,'','Ajency',NULL,8),(10,'Order Online','Order Online',NULL,1),(11,'Book Table','Book Table',NULL,1),(12,'Trending','Trending',NULL,1),(13,'Mobile','Mobile',NULL,8),(14,'Air Conditioners','Air Conditioners',NULL,8),(15,'Televisions ','Televisions ',NULL,8),(16,'Grocery','Grocery',NULL,10),(17,'Bakery ','Bakery ',NULL,10),(18,'AC Repairs','AC Repairs',NULL,11),(19,'laptop  Repairs','laptop  Repairs',NULL,11),(20,'New Cars','New Cars',NULL,12),(21,'Used Cars','Used Cars',NULL,12),(22,'Placement Services','Placement Services',NULL,14),(23,'Search Jobs','Search Jobs',NULL,14),(24,'Overseas Jobs','Overseas Jobs',NULL,14),(25,'Beauty Parlours','Beauty Parlours',NULL,15),(26,'Spas','Spas',NULL,15),(27,'Salons','Salons',NULL,15),(28,'Buy','Buy',NULL,16),(29,'Sell','Sell',NULL,16),(30,'Residential Rental','Residential Rental',NULL,16),(31,'PG Accommodation','PG Accommodation',NULL,16);
/*!40000 ALTER TABLE `sub_category` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-09 22:35:00
