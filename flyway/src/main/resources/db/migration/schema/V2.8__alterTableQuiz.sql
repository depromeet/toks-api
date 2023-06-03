ALTER TABLE `quiz`
    ADD COLUMN `title` VARCHAR(512) NOT NULL COMMENT '퀴즈 제목' AFTER `category_id`;
