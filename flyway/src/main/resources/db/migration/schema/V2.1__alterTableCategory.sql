--
-- Table structure for table `category`
--

ALTER TABLE `category`
    CHANGE COLUMN `createdAt` `created_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일' ,
    CHANGE COLUMN `modifiedAt` `modified_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일' ;
