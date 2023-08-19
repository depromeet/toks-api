CREATE TABLE `quiz_tag`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `quiz_id`    bigint       NOT NULL COMMENT '퀴즈 id',
    `tag_id`     varchar(512) NOT NULL COMMENT '태그 id',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 태그 매핑 테이블';
