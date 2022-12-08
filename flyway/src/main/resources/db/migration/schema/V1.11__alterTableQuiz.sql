--
-- Table structure for table `quiz`
--

ALTER TABLE `quiz` RENAME COLUMN `imageUrl` TO `imageUrls`;
ALTER TABLE `quiz` MODIFY `imageUrls` JSON;
ALTER TABLE `quiz` DROP COLUMN `difficulty`;
