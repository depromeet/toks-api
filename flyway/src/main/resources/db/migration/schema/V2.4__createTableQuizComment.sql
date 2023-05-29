--
-- Table structure for table `quiz_comment`
--

DROP TABLE IF EXISTS `quiz_comment`;
SET
character_set_client = utf8mb4;
CREATE TABLE `quiz_comment`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '퀴즈 댓글 번호',
    `quiz_id`    bigint   NOT NULL COMMENT '퀴즈 id',
    `uid`        bigint   NOT NULL COMMENT 'uid',
    `content`    text     NOT NULL COMMENT '퀴즈 내용',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='스터디 테이블';
