--
-- Table structure for table `study`
--
DROP TABLE IF EXISTS `study`;
SET character_set_client = utf8mb4;
CREATE TABLE `study` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '스터디 번호',
                        `name` varchar(100) NOT NULL COMMENT '스터디 이름',
                        `description` varchar(200) NOT NULL COMMENT '스터디 설명',
                        `start_date` date NOT NULL COMMENT '시작 일자',
                        `end_date` date NOT NULL COMMENT '종료 일자',
                        `status` varchar(50) NOT NULL COMMENT '스터디 상태',
                        `capacity` varchar(50) NOT NULL COMMENT '참여 규모',
                        `study_user_count` int NOT NULL COMMENT '참여 인원',
                        `leader_id` bigint NOT NULL COMMENT '스터디장 user_id',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='스터디 테이블';