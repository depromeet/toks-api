--
-- Table structure for table `quiz`
--

ALTER TABLE `quiz` RENAME COLUMN `quiz` TO `question`;
ALTER TABLE `quiz` ADD `round` tinyint NOT NULL COMMENT '퀴즈 회차'
