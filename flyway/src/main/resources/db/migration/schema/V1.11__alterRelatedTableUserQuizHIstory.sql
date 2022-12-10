--
-- Table structure for table `user_quiz_history`
--
ALTER TABLE `user_quiz_history` RENAME TO `quiz_reply_history`;

ALTER TABLE `quiz_like` RENAME COLUMN `user_quiz_history_id` TO `quiz_reply_history_id`;
