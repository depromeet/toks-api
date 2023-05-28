--
-- Table structure for table `category`
--
DROP TABLE IF EXISTS `category`;
SET character_set_client = utf8mb4;

CREATE TABLE `category`
(
    `id`          varchar(256) NOT NULL COMMENT '카테고리 Id',
    `depth`       int          NOT NULL COMMENT '카테고리 depth',
    `name`        varchar(256) NOT NULL COMMENT '카테고리 name',
    `description` varchar(512) NOT NULL COMMENT '카테고리 설명',
    `image_url`   varchar(512) DEFAULT NULL COMMENT '카테고리 이미지',
    `createdAt`   datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `modifiedAt`  datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='카테고리';
