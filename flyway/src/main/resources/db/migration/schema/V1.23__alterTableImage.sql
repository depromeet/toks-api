--
-- Table structure for table `image`
--

ALTER TABLE `image` MODIFY column `image_url` VARCHAR(2047);
ALTER TABLE `image` ADD COLUMN `extra` VARCHAR(255);