--
-- Table structure for table `quiz_comment_like`
--

DROP TABLE IF EXISTS `quiz_comment_like`;
SET
character_set_client = utf8mb4;
CREATE TABLE `quiz_comment_like`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '퀴즈 좋아요 id',
    `quiz_id`    bigint   NOT NULL COMMENT '퀴즈 id',
    `uid`        bigint   NOT NULL COMMENT 'uid',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 댓글 좋아요 테이블';
