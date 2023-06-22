-- 불필요한 퀴즈 컬럼 정보 삭제
ALTER TABLE `quiz`
DROP COLUMN `round`,
DROP COLUMN `study_id`;

-- 불필요한 스터디 도메인 삭제
drop table study;
drop table study_tag;
drop table study_user;
