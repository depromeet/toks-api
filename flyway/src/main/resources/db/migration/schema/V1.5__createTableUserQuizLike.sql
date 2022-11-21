--
-- Table structure for table `user_quiz_like`
--
DROP TABLE IF EXISTS `user_quiz_like`;
SET character_set_client = utf8mb4;
CREATE TABLE `user_quiz_like` (
                        `id` bigint NOT NULL AUTO_INCREMENT,
                        `user_quiz_history_id` bigint NOT NULL COMMENT '답변 id',
                        `created_by` bigint NOT NULL COMMENT '생성자',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='추천 답변 이력 테이블';

