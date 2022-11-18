--
-- Table structure for table `tag`
--
DROP TABLE IF EXISTS `tag`;
SET character_set_client = utf8mb4;
CREATE TABLE `tag` (
                       `id` bigint NOT NULL AUTO_INCREMENT COMMENT '태그 번호',
                       `name` varchar(100) NOT NULL COMMENT '태그 이름',
                       `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                       `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                       PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='태그 테이블';