--
-- Table structure for table `image`
--
DROP TABLE IF EXISTS `image`;
SET character_set_client = utf8mb4;
CREATE TABLE `image` (
                           `id` bigint NOT NULL AUTO_INCREMENT,
                           `image_url` varchar(512) NOT NULL COMMENT '퀴즈 url',
                           `created_by` bigint NOT NULL COMMENT '생성 user_id',
                           `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                           `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='이미지 테이블';
