-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.23-log - MySQL Community Server (GPL)
-- Server OS:                    Win32
-- HeidiSQL Version:             9.3.0.5109
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Dumping structure for table bestdeals.company
CREATE TABLE IF NOT EXISTS `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `changed_by` varchar(255) DEFAULT NULL,
  `changed_date` datetime DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `company_type` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `city_id` bigint(20) DEFAULT NULL,
  `country_id` bigint(20) DEFAULT NULL,
  `state_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK11ll1ewpdxjg9bm3jtg858qn8` (`city_id`),
  KEY `FKaa85rotlnir4w4xlj1nkilnws` (`country_id`),
  KEY `FKbjr5ppd0h3a70bvcnefrd12oi` (`state_id`),
  KEY `FKdy4v2yb46hefqicjpyj7b7e4s` (`user_id`),
  CONSTRAINT `FK11ll1ewpdxjg9bm3jtg858qn8` FOREIGN KEY (`city_id`) REFERENCES `city` (`id`),
  CONSTRAINT `FKaa85rotlnir4w4xlj1nkilnws` FOREIGN KEY (`country_id`) REFERENCES `country` (`id`),
  CONSTRAINT `FKbjr5ppd0h3a70bvcnefrd12oi` FOREIGN KEY (`state_id`) REFERENCES `state` (`id`),
  CONSTRAINT `FKdy4v2yb46hefqicjpyj7b7e4s` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- Dumping data for table bestdeals.company: ~1 rows (approximately)
DELETE FROM `company`;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` (`id`, `changed_by`, `changed_date`, `create_date`, `created_by`, `address1`, `address2`, `company_type`, `mobile`, `name`, `phone`, `city_id`, `country_id`, `state_id`, `user_id`, `email`) VALUES
	(1, NULL, '2016-08-25 08:36:49', '2016-08-25 08:36:49', NULL, 'Manickam Palayam', NULL, 'OWNER', '9637752262', 'MyShoppy', '9637752262', 1, 1, 1, 1, NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
