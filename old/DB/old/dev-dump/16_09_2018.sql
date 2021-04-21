-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: nce_db
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `emp_customer_table`
--

DROP TABLE IF EXISTS `emp_customer_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `emp_customer_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `alternate_number` varchar(90) DEFAULT NULL,
  `email_id` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `emp_product_table`
--

DROP TABLE IF EXISTS `emp_product_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `emp_product_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `brandName` varchar(100) DEFAULT NULL,
  `brandModel` varchar(100) DEFAULT NULL,
  `serialNumber` varchar(100) DEFAULT NULL,
  `price` int(30) DEFAULT NULL,
  `tax_type` varchar(30) DEFAULT NULL,
  `stockQuantity` int(30) DEFAULT NULL,
  `isService` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `payment_details_table`
--

DROP TABLE IF EXISTS `payment_details_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
  `amount` float(40,2) DEFAULT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=385 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `repair_invoice_table`
--

DROP TABLE IF EXISTS `repair_invoice_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `repair_invoice_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `actualInvoiceId` varchar(60) DEFAULT NULL,
  `defaultValue` varchar(60) NOT NULL DEFAULT 'NCE/2017-18/',
  `vat_tin_number` varchar(60) DEFAULT '27AAJPN5433F1ZO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actualInvoiceId` (`actualInvoiceId`)
) ENGINE=InnoDB AUTO_INCREMENT=864 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sales_invoice_table`
--

DROP TABLE IF EXISTS `sales_invoice_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sales_invoice_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `actualInvoiceId` varchar(60) DEFAULT NULL,
  `defaultValue` varchar(60) DEFAULT 'CE/2017-18/',
  `vat_tin_number` varchar(60) DEFAULT '27AAJPN5433F1ZO',
  PRIMARY KEY (`id`),
  UNIQUE KEY `actualInvoiceId` (`actualInvoiceId`)
) ENGINE=InnoDB AUTO_INCREMENT=268 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sales_order_table`
--

DROP TABLE IF EXISTS `sales_order_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
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
) ENGINE=InnoDB AUTO_INCREMENT=166 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_customer_table`
--

DROP TABLE IF EXISTS `service_customer_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service_customer_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_info_table`
--

DROP TABLE IF EXISTS `service_info_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service_info_table` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `productName` varchar(1200) DEFAULT NULL,
  `productModel` varchar(600) DEFAULT NULL,
  `productSN` varchar(60) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `accessoryList` varchar(1200) DEFAULT NULL,
  `problemList` varchar(1200) DEFAULT NULL,
  `isCourier` tinyint(1) DEFAULT '0',
  `courierName` varchar(1200) DEFAULT NULL,
  `courierPhone` varchar(250) DEFAULT NULL,
  `techComment` varchar(1200) DEFAULT NULL,
  `shopUserComment` varchar(1200) DEFAULT NULL,
  `customerComment` varchar(1200) DEFAULT NULL,
  `serviceStatus` varchar(200) DEFAULT NULL,
  `courierDocumentNo` varchar(300) DEFAULT NULL,
  `service_order_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `service_completion_date` timestamp NULL DEFAULT NULL,
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
  `technician_handle_comment` varchar(1200) DEFAULT NULL,
  `customer_approval_status` varchar(80) DEFAULT 'NA',
  `customer_approval_comment` varchar(1200) DEFAULT NULL,
  `part_pending_status` varchar(80) DEFAULT 'NA',
  `part_pending_comment` varchar(1200) DEFAULT NULL,
  `technician_handle_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `customer_approval_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `part_pending_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `completed_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `tech_completed_comment` varchar(1200) DEFAULT NULL,
  `isOutwardCourier` tinyint(1) DEFAULT '0',
  `outwardCourierName` varchar(550) DEFAULT NULL,
  `outwardCourierPhone` varchar(30) DEFAULT NULL,
  `outwardCourierDocumentNo` varchar(55) DEFAULT NULL,
  `estimated_delivery_cost` varchar(100) DEFAULT 'NA',
  `estimated_delivery_date` varchar(100) DEFAULT 'NA',
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  KEY `userId` (`userId`),
  CONSTRAINT `service_info_table_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `emp_customer_table` (`id`),
  CONSTRAINT `service_info_table_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `validemporiumuser` (`empid`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service_info_table1`
--

DROP TABLE IF EXISTS `service_info_table1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `service_info_table1` (
  `id` int(30) NOT NULL AUTO_INCREMENT,
  `customerId` int(11) NOT NULL,
  `productName` varchar(60) DEFAULT NULL,
  `productModel` varchar(60) DEFAULT NULL,
  `productSN` varchar(60) DEFAULT NULL,
  `userId` int(11) NOT NULL,
  `accessoryList` varchar(60) DEFAULT NULL,
  `problemList` varchar(60) DEFAULT NULL,
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
  PRIMARY KEY (`id`),
  KEY `customerId` (`customerId`),
  KEY `userId` (`userId`),
  CONSTRAINT `service_info_table1_ibfk_1` FOREIGN KEY (`customerId`) REFERENCES `emp_customer_table` (`id`),
  CONSTRAINT `service_info_table1_ibfk_2` FOREIGN KEY (`userId`) REFERENCES `validemporiumuser` (`empid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `validemporiumuser`
--

DROP TABLE IF EXISTS `validemporiumuser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `validemporiumuser` (
  `empId` int(11) NOT NULL AUTO_INCREMENT,
  `empName` varchar(20) DEFAULT NULL,
  `empPassword` varchar(20) DEFAULT NULL,
  `empRole` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-16 21:45:34
