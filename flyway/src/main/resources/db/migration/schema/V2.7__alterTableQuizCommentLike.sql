ALTER TABLE `tdsn`.`quiz_comment_like`
    ADD COLUMN `comment_id` BIGINT NOT NULL COMMENT 'Quiz comment id' AFTER `updated_at`;
