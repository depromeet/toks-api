--
-- Table structure for table `category`
--

ALTER TABLE `quiz`
    ADD COLUMN `category_id` VARCHAR(256) NOT NULL COMMENT '카테고리 id' AFTER `round`;
