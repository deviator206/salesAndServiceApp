/*
SQLyog Community v12.09 (64 bit)
MySQL - 5.6.35-log : Database - nce_db
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`nce_db` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `nce_db`;

/*Table structure for table `emp_customer_table` */

DROP TABLE IF EXISTS `emp_customer_table`;

CREATE TABLE `emp_customer_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `alternate_number` varchar(90) DEFAULT NULL,
  `email_id` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;

/*Table structure for table `emp_product_table` */

DROP TABLE IF EXISTS `emp_product_table`;

CREATE TABLE `emp_product_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brandName` varchar(20) DEFAULT NULL,
  `brandModel` varchar(30) DEFAULT NULL,
  `serialNumber` varchar(20) DEFAULT NULL,
  `price` int(30) DEFAULT NULL,
  `tax_type` varchar(30) DEFAULT NULL,
  `stockQuantity` int(30) DEFAULT NULL,
  `isService` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Table structure for table `payment_details_table` */

DROP TABLE IF EXISTS `payment_details_table`;

CREATE TABLE `payment_details_table` (
  `id` int(40) NOT NULL AUTO_INCREMENT,
  `cash` varchar(60) DEFAULT NULL,
  `cheqNo` varchar(60) DEFAULT NULL,
  `bankName` varchar(60) DEFAULT NULL,
  `ifscCode` varchar(60) DEFAULT NULL,
  `cheqDate` varchar(60) DEFAULT NULL,
  `accountNo` varchar(60) DEFAULT NULL,
  `cardNo` varchar(60) DEFAULT NULL,
  `invoice_id` varchar(60) DEFAULT NULL,
  `invoice_tin` varchar(60) DEFAULT NULL,
  `amount` float(10,2) DEFAULT NULL,
  `onlinePaymentMode` varchar(55) DEFAULT NULL,
  `onlineTransactionId` varchar(55) DEFAULT NULL,
  `onlineRemark` varchar(55) DEFAULT NULL,
  `cardExpiryDate` varchar(55) DEFAULT NULL,
  `cardNetwork` varchar(55) DEFAULT NULL,
  `cardBank` varchar(55) DEFAULT NULL,
  `final_cash` varchar(60) DEFAULT NULL,
  `final_cheqNo` varchar(60) DEFAULT NULL,
  `final_cheqDate` varchar(60) DEFAULT NULL,
  `final_bankName` varchar(60) DEFAULT NULL,
  `final_cardNo` varchar(60) DEFAULT NULL,
  `final_cardNetwork` varchar(60) DEFAULT NULL,
  `final_onlinePaymentMode` varchar(60) DEFAULT NULL,
  `final_onlineTransactionId` varchar(60) DEFAULT NULL,
  `final_onlineRemark` varchar(60) DEFAULT NULL,
  `final_amount` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

/*Table structure for table `repair_invoice_table` */

DROP TABLE IF EXISTS `repair_invoice_table`;

CREATE TABLE `repair_invoice_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `actualInvoiceId` varchar(60) DEFAULT NULL,
  `defaultValue` varchar(60) NOT NULL DEFAULT 'NCE/2017-18/',
  `vat_tin_number` varchar(60) DEFAULT '2763039355V',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actualInvoiceId` (`actualInvoiceId`)
) ENGINE=InnoDB AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

/*Table structure for table `sales_invoice_table` */

DROP TABLE IF EXISTS `sales_invoice_table`;

CREATE TABLE `sales_invoice_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `actualInvoiceId` varchar(60) DEFAULT NULL,
  `defaultValue` varchar(60) DEFAULT 'CE/2017-18/',
  `vat_tin_number` varchar(60) DEFAULT '2763039355V',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actualInvoiceId` (`actualInvoiceId`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

/*Table structure for table `sales_order_table` */

DROP TABLE IF EXISTS `sales_order_table`;

CREATE TABLE `sales_order_table` (
  `sale_id` int(30) NOT NULL AUTO_INCREMENT,
  `invoice_id` varchar(60) NOT NULL,
  `customer_id` varchar(60) NOT NULL,
  `product_id` varchar(60) NOT NULL,
  `product_unit_price` float(10,2) NOT NULL,
  `product_qty` float(10,2) NOT NULL,
  `product_price_with_qty` float(10,2) NOT NULL,
  `tax_type` varchar(60) DEFAULT NULL,
  `tax_rate` varchar(60) DEFAULT NULL,
  `order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tax_amount` float(10,2) DEFAULT NULL,
  `total_amount` float(10,2) NOT NULL,
  `tax_value` float(10,2) DEFAULT NULL,
  PRIMARY KEY (`sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;

/*Table structure for table `service_customer_table` */

DROP TABLE IF EXISTS `service_customer_table`;

CREATE TABLE `service_customer_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `service_info_table` */

DROP TABLE IF EXISTS `service_info_table`;

CREATE TABLE `service_info_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `productName` varchar(60) DEFAULT NULL,
  `productModel` varchar(60) DEFAULT NULL,
  `productSN` varchar(60) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `accessoryList` varchar(255) DEFAULT NULL,
  `problemList` varchar(255) DEFAULT NULL,
  `isCourier` tinyint(1) DEFAULT '0',
  `courierName` varchar(30) DEFAULT NULL,
  `courierPhone` varchar(30) DEFAULT NULL,
  `techComment` varchar(120) DEFAULT NULL,
  `shopUserComment` varchar(120) DEFAULT NULL,
  `customerComment` varchar(120) DEFAULT NULL,
  `serviceStatus` varchar(20) DEFAULT NULL,
  `courierDocumentNo` varchar(30) DEFAULT NULL,
  `service_order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `service_completion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tentative_service_completion_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tentative_quoted_cost` varchar(40) DEFAULT '0',
  `actual_service_cost` varchar(40) DEFAULT NULL,
  `service_order_number` varchar(50) NOT NULL,
  `taxName` varchar(40) DEFAULT NULL,
  `taxRate` varchar(40) DEFAULT NULL,
  `taxValue` varchar(40) DEFAULT NULL,
  `vatTinNumber` varchar(60) DEFAULT NULL,
  `advancedPayment` varchar(40) DEFAULT NULL,
  `finalPayment` varchar(40) DEFAULT NULL,
  `finalDeleted` varchar(255) DEFAULT NULL,
  `technician_handle_status` varchar(80) DEFAULT 'NA',
  `technician_handle_comment` varchar(255) DEFAULT NULL,
  `customer_approval_status` varchar(80) DEFAULT 'NA',
  `customer_approval_comment` varchar(255) DEFAULT NULL,
  `part_pending_status` varchar(80) DEFAULT 'NA',
  `part_pending_comment` varchar(255) DEFAULT NULL,
  `technician_handle_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_approval_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `part_pending_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `completed_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tech_completed_comment` varchar(255) DEFAULT NULL,
  `isOutwardCourier` tinyint(1) DEFAULT '0',
  `outwardCourierName` varchar(55) DEFAULT NULL,
  `outwardCourierPhone` varchar(30) DEFAULT NULL,
  `outwardCourierDocumentNo` varchar(55) DEFAULT NULL,
  `estimated_delivery_cost` varchar(100) DEFAULT 'NA',
  `estimated_delivery_date` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  KEY `userId` (`userId`),
  CONSTRAINT `service_info_table_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `emp_customer_table` (`id`),
  CONSTRAINT `service_info_table_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `validemporiumuser` (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=106 DEFAULT CHARSET=utf8;

/*Table structure for table `validemporiumuser` */

DROP TABLE IF EXISTS `validemporiumuser`;

CREATE TABLE `validemporiumuser` (
  `empId` int(11) NOT NULL AUTO_INCREMENT,
  `empName` varchar(20) DEFAULT NULL,
  `empPassword` varchar(20) DEFAULT NULL,
  `empRole` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
