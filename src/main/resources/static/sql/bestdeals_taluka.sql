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
-- Table structure for table `taluka`
--

DROP TABLE IF EXISTS `taluka`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `taluka` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtm76kyajd6lefp1ijmtphr1h2` (`city_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `taluka`
--

LOCK TABLES `taluka` WRITE;
/*!40000 ALTER TABLE `taluka` DISABLE KEYS */;
INSERT INTO `taluka` VALUES (280,'Walwa',26),(279,'Tasgaon',26),(278,'Shirala',26),(277,'Palus',26),(276,'Miraj',26),(275,'Khanapur',26),(274,'Kavathemahankal',26),(273,'Kadegaon',26),(272,'Jat',26),(56,'Georai',6),(57,'Kaij',6),(58,'Manjlegaon',6),(59,'Parli',6),(60,'Patoda',6),(61,'Shirur (Kasar)',6),(62,'Wadwani',6),(63,'Buldana',7),(64,'Chikhli',7),(65,'Deolgaon Raja',7),(66,'Jalgaon (Jamod)',7),(67,'Khamgaon',7),(68,'Lonar',7),(69,'Malkapur',7),(70,'Mehkar',7),(71,'Motala',7),(72,'Nandura',7),(73,'Sangrampur',7),(74,'Shegaon',7),(75,'Sindkhed Raja',7),(76,'Ballarpur',8),(77,'Bhadravati',8),(78,'Brahmapuri',8),(79,'Chandrapur',8),(80,'Chimur',8),(81,'Gondpipri',8),(82,'Jiwati',8),(83,'Korpana',8),(84,'Mul',8),(85,'Nagbhir',8),(86,'Pombhurna',8),(87,'Rajura',8),(88,'Sawali',8),(89,'Sindewahi',8),(90,'Warora',8),(91,'Dhule',9),(92,'Sakri',9),(93,'Shirpur',9),(94,'Sindkhede',9),(95,'Aheri',10),(96,'Armori',10),(97,'Bhamragad',10),(98,'Chamorshi',10),(99,'Desaiganj (Vadasa)',10),(100,'Dhanora',10),(101,'Etapalli',10),(102,'Gadchiroli',10),(103,'Korchi',10),(104,'Kurkheda',10),(105,'Mulchera',10),(106,'Sironcha',10),(107,'Amgaon',11),(108,'Arjuni Morgaon',11),(109,'Deori',11),(110,'Gondiya',11),(111,'Goregaon',11),(112,'Sadak-Arjuni',11),(113,'Salekasa',11),(114,'Tirora',11),(115,'Aundha (Nagnath)',12),(116,'Basmath',12),(117,'Hingoli',12),(118,'Kalamnuri',12),(119,'Sengaon',12),(120,'Amalner',13),(121,'Bhadgaon',13),(122,'Bhusawal',13),(123,'Bodvad',13),(124,'Chalisgaon',13),(125,'Chopda',13),(126,'Dharangaon',13),(127,'Erandol',13),(128,'Jalgaon',13),(129,'Jamner',13),(130,'Muktainagar (Edlabad)',13),(131,'Pachora',13),(132,'Parola',13),(133,'Raver',13),(134,'Yawal',13),(135,'Ambad',14),(136,'Badnapur',14),(137,'Bhokardan',14),(138,'Ghansawangi',14),(139,'Jafferabad',14),(140,'Jalna',14),(141,'Mantha',14),(142,'Partur',14),(143,'Ajra',15),(144,'Bavda',15),(145,'Bhudargad',15),(146,'Chandgad',15),(147,'Gadhinglaj',15),(148,'Hatkanangle',15),(149,'Kagal',15),(150,'Karvir',15),(151,'Panhala',15),(152,'Radhanagari',15),(153,'Shahuwadi',15),(154,'Shirol',15),(155,'Ahmadpur',16),(156,'Ausa',16),(157,'Chakur',16),(158,'Deoni',16),(159,'Jalkot',16),(160,'Latur',16),(161,'Nilanga',16),(162,'Renapur',16),(163,'Shirur-Anantpal',16),(164,'Udgir',16),(165,'Bhiwapur',17),(166,'Hingna',17),(167,'Kalameshwar',17),(168,'Kamptee',17),(169,'Katol',17),(170,'Kuhi',17),(171,'Mauda',17),(172,'Nagpur (Rural)',17),(173,'Nagpur (Urban)',17),(174,'Narkhed',17),(175,'Parseoni',17),(176,'Ramtek',17),(177,'Savner',17),(178,'Umred',17),(179,'Ardhapur',18),(180,'Bhokar',18),(181,'Biloli',18),(182,'Deglur',18),(183,'Dharmabad',18),(184,'Hadgaon',18),(185,'Himayatnagar',18),(186,'Kandhar',18),(187,'Kinwat',18),(188,'Loha',18),(189,'Mahoor',18),(190,'Mudkhed',18),(191,'Mukhed',18),(192,'Naigaon (Khairgaon)',18),(193,'Nanded',18),(194,'Umri',18),(195,'Akkalkuwa',19),(196,'Akrani',19),(197,'Nandurbar',19),(198,'Nawapur',19),(199,'Shahade',19),(200,'Talode',19),(201,'Baglan',20),(202,'Chandvad',20),(203,'Deola',20),(204,'Dindori',20),(205,'Igatpuri',20),(206,'Kalwan',20),(207,'Malegaon',20),(208,'Nandgaon',20),(209,'Nashik',20),(210,'Niphad',20),(211,'Peint',20),(212,'Sinnar',20),(213,'Surgana',20),(214,'Trimbakeshwar',20),(215,'Yevla',20),(216,'Bhum',21),(217,'Kalamb',21),(218,'Lohara',21),(219,'Osmanabad',21),(220,'Paranda',21),(221,'Tuljapur',21),(222,'Umarga',21),(223,'Washi',21),(224,'Gangakhed',22),(225,'Jintur',22),(226,'Manwath',22),(227,'Palam',22),(228,'Parbhani',22),(229,'Pathri',22),(230,'Purna',22),(231,'Sailu',22),(232,'Sonpeth',22),(233,'Ambegaon',23),(234,'Baramati',23),(235,'Bhor',23),(236,'Daund',23),(237,'Haveli',23),(238,'Indapur',23),(239,'Junnar',23),(240,'Khed',23),(241,'Mawal',23),(242,'Mulshi',23),(243,'Pune City',23),(244,'Purandhar',23),(245,'Shirur',23),(246,'Velhe',23),(247,'Alibag',24),(248,'Karjat',24),(249,'Khalapur',24),(250,'Mahad',24),(251,'Mangaon',24),(252,'Mhasla',24),(253,'Murud',24),(254,'Panvel',24),(255,'Pen',24),(256,'Poladpur',24),(257,'Roha',24),(258,'Shrivardhan',24),(259,'Sudhagad',24),(260,'Tala',24),(261,'Uran',24),(262,'Chiplun',25),(263,'Dapoli',25),(264,'Guhagar',25),(265,'Khed',25),(266,'Lanja',25),(267,'Mandangad',25),(268,'Rajapur',25),(269,'Ratnagiri',25),(270,'Sangameshwar',25),(271,'Atpadi',26),(55,'Dharur',6),(54,'Bid',6),(53,'Ashti',6),(52,'Ambejogai',6),(51,'Tumsar',5),(50,'Sakoli',5),(49,'Pauni',5),(48,'Mohadi',5),(47,'Lakhani',5),(46,'Lakhandur',5),(45,'Bhandara',5),(44,'Vaijapur',4),(43,'Soegaon',4),(42,'Sillod',4),(41,'Phulambri',4),(40,'Paithan',4),(39,'Khuldabad',4),(38,'Kannad',4),(37,'Gangapur',4),(36,'Aurangabad',4),(35,'Warud',3),(34,'Teosa',3),(33,'Nandgaon-Khandeshwar',3),(32,'Morshi',3),(31,'Dharni',3),(30,'Dhamangaon Railway',3),(29,'Daryapur',3),(28,'Chikhaldara',3),(27,'Chandurbazar',3),(26,'Chandur Railway',3),(25,'Bhatkuli',3),(24,'Anjangaon Surji',3),(23,'Amravati',3),(22,'Achalpur',3),(21,'Telhara',2),(20,'Patur',2),(19,'Murtijapur',2),(18,'Barshitakli',2),(17,'Balapur',2),(16,'Akot',2),(15,'Akola',2),(14,'Shrirampur',1),(13,'Shrigonda',1),(12,'Shevgaon',1),(11,'Sangamner',1),(10,'Rahuri',1),(9,'Rahta',1),(8,'Pathardi',1),(7,'Parner',1),(6,'Nevasa',1),(5,'Nagar',1),(4,'Kopargaon',1),(3,'Karjat',1),(2,'Jamkhed',1),(1,'Akola-Nager',1),(281,'Jaoli',27),(282,'Karad',27),(283,'Khandala',27),(284,'Khatav',27),(285,'Koregaon',27),(286,'Mahabaleshwar',27),(287,'Man',27),(288,'Patan',27),(289,'Phaltan',27),(290,'Satara',27),(291,'Wai',27),(292,'Devgad',28),(293,'Dodamarg',28),(294,'Kankavli',28),(295,'Kudal',28),(296,'Malwan',28),(297,'Sawantwadi',28),(298,'Vaibhavvadi',28),(299,'Vengurla',28),(300,'Akkalkot',29),(301,'Barshi',29),(302,'Karmala',29),(303,'Madha',29),(304,'Malshiras',29),(305,'Mangalvedhe',29),(306,'Mohol',29),(307,'Pandharpur',29),(308,'Sangole',29),(309,'Solapur North',29),(310,'Solapur South',29),(311,'Ambarnath',30),(312,'Bhiwandi',30),(313,'Dahanu',30),(314,'Jawhar',30),(315,'Kalyan',30),(316,'Mokhada',30),(317,'Murbad',30),(318,'Palghar',30),(319,'Shahapur',30),(320,'Talasari',30),(321,'Thane',30),(322,'Ulhasnagar',30),(323,'Vada',30),(324,'Vasai',30),(325,'Vikramgad',30),(326,'Arvi',31),(327,'Ashti',31),(328,'Deoli',31),(329,'Hinganghat',31),(330,'Karanja',31),(331,'Samudrapur',31),(332,'Seloo',31),(333,'Wardha',31),(334,'Karanja',32),(335,'Malegaon',32),(336,'Mangrulpir',32),(337,'Manora',32),(338,'Risod',32),(339,'Washim',32),(340,'Arni',33),(341,'Babulgaon',33),(342,'Darwha',33),(343,'Digras',33),(344,'Ghatanji',33),(345,'Kalamb',33),(346,'Kelapur',33),(347,'Mahagaon',33),(348,'Maregaon',33),(349,'Ner',33),(350,'Pusad',33),(351,'Ralegaon',33),(352,'Umarkhed',33),(353,'Wani',33),(354,'Yavatmal',33),(355,'Zari-Jamani',33);
/*!40000 ALTER TABLE `taluka` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-10-09 22:35:26
