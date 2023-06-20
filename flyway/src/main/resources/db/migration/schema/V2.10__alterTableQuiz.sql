ALTER TABLE `quiz`
    CHANGE COLUMN `started_at` `started_at` DATETIME NULL DEFAULT NULL COMMENT '시작시간',
    CHANGE COLUMN `ended_at` `ended_at` DATETIME NULL DEFAULT NULL COMMENT '종료시간',
    CHANGE COLUMN `study_id` `study_id` BIGINT NULL DEFAULT NULL COMMENT '스터디 id',
    CHANGE COLUMN `round` `round` TINYINT NULL DEFAULT NULL COMMENT '퀴즈 회차',
    CHANGE COLUMN `image_urls` `image_urls` VARCHAR (512) NULL DEFAULT NULL '이미지 url 리스트';
