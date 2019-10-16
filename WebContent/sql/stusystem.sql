/*
SQLyog Ultimate v11.25 (64 bit)
MySQL - 5.5.56 : Database - stusystem
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`stusystem` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `stusystem`;

/*Table structure for table `standard` */

DROP TABLE IF EXISTS `standard`;

CREATE TABLE `standard` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '使用自动增长赋值',
  `stdNum` varchar(50) NOT NULL,
  `zhname` varchar(50) NOT NULL,
  `version` varchar(50) NOT NULL,
  `keys` varchar(50) NOT NULL,
  `releaseDate` date DEFAULT NULL,
  `implDate` date DEFAULT NULL,
  `packagePath` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `stdNum` (`stdNum`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

/*Data for the table `standard` */

insert  into `standard`(`id`,`stdNum`,`zhname`,`version`,`keys`,`releaseDate`,`implDate`,`packagePath`) values (1,'GB 6657','玩具危险性','2015','玩具','2017-07-02','2017-07-02','123456.jpg'),(2,'GB 6658','玻璃杯','2016','玻璃','2017-07-02','2017-07-02','11.jpg'),(3,'GB 6659','玻璃杯','2016','玻璃','2017-07-02','2017-07-15','1499002745482.jpg'),(4,'GB 6660','玻璃杯','2016','玻璃','2017-07-02','2017-07-15','1499003624512.jpg'),(5,'GB 6661','玻璃杯','2016','玻璃','2017-07-02','2017-07-05','1499004288393.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
