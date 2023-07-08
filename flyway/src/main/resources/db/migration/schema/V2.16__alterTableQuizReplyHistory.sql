ALTER TABLE `tdsn`.`quiz_reply_history`
    ADD COLUMN `ip_address` VARCHAR(256) NOT NULL AFTER `quiz_id`;
