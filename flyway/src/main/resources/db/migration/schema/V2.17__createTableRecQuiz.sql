CREATE TABLE `rec_quiz`
(
    `id`          bigint                                  NOT NULL AUTO_INCREMENT,
    `category_id` varchar(128) COLLATE utf8mb4_general_ci NOT NULL COMMENT '카테고리 id',
    `pids`        varchar(512) COLLATE utf8mb4_general_ci NOT NULL COMMENT '추천 Pid 목록',
    `round`       int                                     NOT NULL COMMENT '회차',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
