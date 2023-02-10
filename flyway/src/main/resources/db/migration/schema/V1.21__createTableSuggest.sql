DROP TABLE IF EXISTS `suggest`;
SET character_set_client = utf8mb4;

CREATE TABLE `suggest`
(
    `id`         bigint NOT NULL AUTO_INCREMENT,
    `uid`        bigint                                  DEFAULT NULL,
    `title`      varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `content`    text COLLATE utf8mb4_general_ci,
    `status`     varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
    `created_at` datetime                                DEFAULT NULL,
    `updated_at` datetime                                DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='제안하기 테이블';
