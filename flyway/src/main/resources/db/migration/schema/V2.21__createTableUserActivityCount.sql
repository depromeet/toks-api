--
-- Table structure for table `user_activity_count`
--

DROP TABLE IF EXISTS `user_activity_count`;
SET
character_set_client = utf8mb4;
CREATE TABLE `user_activity_count`
(
    `id`         bigint   NOT NULL AUTO_INCREMENT COMMENT '사용자 활동 id',
    `user_id`    bigint   NOT NULL COMMENT '사용자 id',
    `total_visit_count`    int     NOT NULL COMMENT '사용자 총 방문 횟수',
    `total_solve_count`    int     NOT NULL COMMENT '사용자 총 푼 문제 수',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
    `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
    PRIMARY KEY (`id`)
) AUTO_INCREMENT=200000 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='사용자 활동 카운트 테이블';
