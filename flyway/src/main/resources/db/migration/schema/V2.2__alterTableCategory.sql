--
-- Table structure for table `category`
--

ALTER TABLE `category`
    CHANGE COLUMN `modified_at` `updated_at` DATETIME NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일' ;
