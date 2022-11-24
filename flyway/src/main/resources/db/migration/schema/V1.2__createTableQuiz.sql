--
-- Table structure for table `quiz`
--
DROP TABLE IF EXISTS `quiz`;
SET character_set_client = utf8mb4;
CREATE TABLE `quiz` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `quiz` varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '퀴즈',
                        `quiz_type` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '퀴즈 타입',
                        `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '퀴즈 설명',
                        `difficulty` tinyint DEFAULT NULL COMMENT '난이도',
                        `answer` json NOT NULL COMMENT '정답',
                        `image_url` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '이미지 url',
                        `started_at` datetime NOT NULL COMMENT '시작시간',
                        `ended_at` datetime NOT NULL COMMENT '종료시간',
                        `study_id` bigint NOT NULL COMMENT '스터디 id',
                        `created_by` bigint NOT NULL COMMENT '생성자',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 테이블';
