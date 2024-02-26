CREATE TABLE `category`
(
    `id`          varchar(256) NOT NULL COMMENT '카테고리 Id',
    `depth`       int          NOT NULL COMMENT '카테고리 depth',
    `name`        varchar(256) NOT NULL COMMENT '카테고리 name',
    `description` varchar(512) NOT NULL COMMENT '카테고리 설명',
    `image_url`   varchar(512) DEFAULT NULL COMMENT '카테고리 이미지',
    `created_at`  datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    `updated_at`  datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='카테고리';

CREATE TABLE `flyway_schema_history`
(
    `installed_rank` int           NOT NULL,
    `version`        varchar(50)            DEFAULT NULL,
    `description`    varchar(200)  NOT NULL,
    `type`           varchar(20)   NOT NULL,
    `script`         varchar(1000) NOT NULL,
    `checksum`       int                    DEFAULT NULL,
    `installed_by`   varchar(100)  NOT NULL,
    `installed_on`   timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `execution_time` int           NOT NULL,
    `success`        tinyint(1) NOT NULL,
    PRIMARY KEY (`installed_rank`),
    KEY              `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `image`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT,
    `image_url`  varchar(2047)     DEFAULT NULL,
    `created_by` bigint   NOT NULL COMMENT '생성 user_id',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    `extra`      varchar(255)      DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COMMENT='이미지 테이블';

CREATE TABLE `quiz`
(
    `id`          bigint                                                       NOT NULL AUTO_INCREMENT,
    `question`    text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '퀴즈',
    `quiz_type`   varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '퀴즈 타입',
    `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci         DEFAULT NULL COMMENT '퀴즈 설명',
    `answer`      text COMMENT '정답',
    `created_by`  bigint                                                       NOT NULL COMMENT '생성자',
    `created_at`  datetime                                                     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`  datetime                                                              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    `category_id` varchar(256)                                                 NOT NULL COMMENT '카테고리 id',
    `title`       varchar(512)                                                 NOT NULL COMMENT '퀴즈 제목',
    `is_deleted`  tinyint(1) NOT NULL,
    `tags`        varchar(512)                                                          DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 테이블';

CREATE TABLE `quiz_comment`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '퀴즈 댓글 번호',
    `quiz_id`    bigint   NOT NULL COMMENT '퀴즈 id',
    `uid`        bigint   NOT NULL COMMENT 'uid',
    `content`    text     NOT NULL COMMENT '퀴즈 내용',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`),
    KEY          `idx_quiz_id` (`quiz_id`),
    KEY          `idx_uid` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=200095 DEFAULT CHARSET=utf8mb4 COMMENT='스터디 테이블';

CREATE TABLE `quiz_comment_like`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '퀴즈 좋아요 id',
    `uid`        bigint   NOT NULL COMMENT 'uid',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    `comment_id` bigint   NOT NULL COMMENT 'Quiz comment id',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 댓글 좋아요 테이블';

CREATE TABLE `quiz_like`
(
    `id`                    bigint   NOT NULL AUTO_INCREMENT,
    `quiz_reply_history_id` bigint   NOT NULL COMMENT '답변 id',
    `created_by`            bigint   NOT NULL COMMENT '생성자',
    `created_at`            datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`            datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COMMENT='추천 답변 이력 테이블';

CREATE TABLE `quiz_rank`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT,
    `score`      tinyint  NOT NULL COMMENT '점수',
    `study_id`   bigint   NOT NULL COMMENT '스터디 id',
    `user_id`    bigint   NOT NULL COMMENT '사용자 id',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='순위 테이블';

CREATE TABLE `quiz_reply_history`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `answer`     text COMMENT '답변',
    `created_by` bigint                DEFAULT NULL COMMENT '생성자',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    `quiz_id`    bigint       NOT NULL COMMENT '퀴즈 id',
    `ip_address` varchar(256) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COMMENT='답변 테이블';

CREATE TABLE `system_action_log`
(
    `id`          bigint NOT NULL AUTO_INCREMENT,
    `created_at`  datetime                                                      DEFAULT NULL,
    `deleted_at`  datetime                                                      DEFAULT NULL,
    `updated_at`  datetime                                                      DEFAULT NULL,
    `host`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `http_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `ip_address`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `path`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `referer`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    `user_agent`  varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=234322 DEFAULT CHARSET=utf8mb4 COMMENT='로깅 테이블';

CREATE TABLE `rec_quiz`
(
    `id`          bigint                                                        NOT NULL AUTO_INCREMENT,
    `category_id` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '카테고리 id',
    `pids`        varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '추천 Pid 목록',
    `round`       int                                                           NOT NULL COMMENT '회차',
    `created_at`  datetime                                                      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`  datetime                                                               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `tag`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT COMMENT '태그 번호',
    `name`       varchar(100) NOT NULL COMMENT '태그 이름',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`),
    UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=utf8mb4 COMMENT='태그 테이블';

CREATE TABLE `user`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
    `email`               varchar(100) NOT NULL COMMENT '회원 이메일',
    `nickname`            varchar(100) NOT NULL COMMENT '회원 이름',
    `status`              varchar(30)  NOT NULL DEFAULT 'ACTIVE' COMMENT '회원 상태',
    `user_role`           varchar(30)  NOT NULL DEFAULT 'USER' COMMENT '회원 권한',
    `thumbnail_image_url` varchar(512) NOT NULL COMMENT '작은 이미지 URL',
    `profile_image_url`   varchar(512) NOT NULL COMMENT '큰 이미지 URL',
    `provider`            varchar(30)  NOT NULL DEFAULT 'IFSELF' COMMENT 'provider(IFSELF, GOOGLE, KAKAO, NAVER)',
    `provider_id`         varchar(100)          DEFAULT NULL COMMENT 'provider 고유 id',
    `created_at`          datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`          datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    `refresh_token`       varchar(1000)         DEFAULT NULL COMMENT '리프래시 토큰',
    PRIMARY KEY (`id`),
    UNIQUE KEY `email` (`email`),
    UNIQUE KEY `user_provider_provider_id_uindex` (`provider`,`provider_id`)
) ENGINE=InnoDB AUTO_INCREMENT=181 DEFAULT CHARSET=utf8mb4 COMMENT='유저 테이블';

CREATE TABLE `user_activity_count`
(
    `id`                bigint   NOT NULL AUTO_INCREMENT COMMENT '사용자 활동 id',
    `user_id`           bigint   NOT NULL COMMENT '사용자 id',
    `total_visit_count` int      NOT NULL COMMENT '사용자 총 방문 횟수',
    `total_solve_count` int      NOT NULL COMMENT '사용자 총 푼 문제 수',
    `created_at`        datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`        datetime          DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200002 DEFAULT CHARSET=utf8mb4 COMMENT='사용자 활동 카운트 테이블';

CREATE TABLE `user_category`
(
    `id`           bigint       NOT NULL AUTO_INCREMENT,
    `user_id`      bigint       NOT NULL COMMENT '사용자 id',
    `category_ids` varchar(512) NOT NULL COMMENT '카테고리 id들',
    `created_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at`   datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COMMENT='사용자 설정 카테고리목록';

CREATE TABLE `quiz_tag`
(
    `id`         bigint       NOT NULL AUTO_INCREMENT,
    `quiz_id`    bigint       NOT NULL COMMENT '퀴즈 id',
    `tag_id`     varchar(512) NOT NULL COMMENT '태그 id',
    `created_at` datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=200000 DEFAULT CHARSET=utf8mb4 COMMENT='퀴즈 태그 매핑 테이블';

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
