ALTER TABLE `tdsn`.`quiz_reply_history`
    CHANGE COLUMN `ip_address` `user_uuid` varchar(255) DEFAULT NULL COMMENT '사용자 UUID';
