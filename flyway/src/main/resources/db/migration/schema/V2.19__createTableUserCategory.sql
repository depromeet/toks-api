--
-- Table structure for table `user_category`
--
DROP TABLE IF EXISTS `user_category`;
SET character_set_client = utf8mb4;
CREATE TABLE `user_category` (
                         `id` bigint NOT NULL AUTO_INCREMENT,
                         `user_id` bigint NOT NULL COMMENT '사용자 id',
                         `category_ids` varchar(512) NOT NULL COMMENT '카테고리 id들',
                         `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                         `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                         PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 설정 카테고리목록';
