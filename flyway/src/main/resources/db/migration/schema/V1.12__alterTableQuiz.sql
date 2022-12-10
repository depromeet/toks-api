--
-- Table structure for table `quiz`
--

ALTER TABLE `quiz` RENAME COLUMN `image_url` TO `image_urls`;
ALTER TABLE `quiz` MODIFY `image_urls` VARCHAR(255) COMMENT '이미지 url 리스트';
ALTER TABLE `quiz` DROP COLUMN `difficulty`;
