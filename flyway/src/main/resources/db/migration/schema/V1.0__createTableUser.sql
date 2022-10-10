--
-- Table structure for table `user`
--
DROP TABLE IF EXISTS `user`;
SET character_set_client = utf8mb4;
CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '회원 번호',
                        `email` varchar(100) NOT NULL UNIQUE COMMENT '회원 이메일',
                        `name` varchar(100) NOT NULL COMMENT '회원 이름',
                        `upwd` varchar(100) NOT NULL COMMENT '회원 비밀번호',
                        `status` varchar(30) NOT NULL DEFAULT 'ACTIVE' COMMENT '회원 상태',
                        `user_role` varchar(30) NOT NULL DEFAULT 'USER' COMMENT '회원 권한',
                        `provider` varchar(30) NOT NULL DEFAULT 'IFSELF' COMMENT 'provider(IFSELF, GOOGLE, KAKAO, NAVER)',
                        `provider_id` varchar(100) NULL COMMENT 'provider 고유 id',
                        `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '등록일시',
                        `updated_at` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정일시',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='유저 테이블';

create unique index user_provider_provider_id_uindex
    on user(provider, provider_id);