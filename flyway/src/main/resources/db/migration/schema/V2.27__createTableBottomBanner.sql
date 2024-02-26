CREATE TABLE `bottom_banner`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `title`       varchar(512) NOT NULL COMMENT '배너명',
    `seq`         int          NOT NULL COMMENT '배너 순서',
    `image_url`   varchar(512) NOT NULL COMMENT '배너 이미지',
    `landing_url` varchar(512) NOT NULL COMMENT '랜딩 url',
    `is_active`   tinyint(1) NOT NULL COMMENT '활성화 여부',
    `created_at`  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`  datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    primary key (id)
) ENGINE = InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COMMENT='바텀 배너';
