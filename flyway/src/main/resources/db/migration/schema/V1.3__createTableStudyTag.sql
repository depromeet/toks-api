--
-- Table structure for table `study_tag`
--
DROP TABLE IF EXISTS `study_tag`;
SET character_set_client = utf8mb4;
CREATE TABLE `study_tag` (
                             `id` bigint NOT NULL AUTO_INCREMENT COMMENT '스터디 태그 번호',
                             `study_id` bigint NOT NULL COMMENT '스터디 번호',
                             `tag_id` varchar(100) NOT NULL COMMENT '태그 번호',
                             `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                             `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                             PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='스터디 태그 테이블';