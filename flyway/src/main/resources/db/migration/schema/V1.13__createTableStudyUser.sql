--
-- Table structure for table `study_user`
--
DROP TABLE IF EXISTS `study_user`;
SET character_set_client = utf8mb4;
CREATE TABLE `study_user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '스터디 유저 번호',
                        `user_id` bigint NOT NULL COMMENT '참여 user_id',
                        `study_id` bigint NOT NULL COMMENT '참여 study_id',
                        `status` varchar(50) NOT NULL COMMENT '스터디 유저 상태',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='스터디 테이블';