--
-- Table structure for table `ranking`
--
DROP TABLE IF EXISTS `ranking`;
SET character_set_client = utf8mb4;
CREATE TABLE `ranking` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `score` tinyint NOT NULL COMMENT '점수',
                        `study_id` bigint NOT NULL COMMENT '스터디 id',
                        `user_id` bigint NOT NULL COMMENT '사용자 id',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='순위 테이블';

