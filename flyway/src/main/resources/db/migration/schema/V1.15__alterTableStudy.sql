--
-- Table structure for table `study`
--

alter table study
    change start_date started_at datetime not null comment '시작 일자';

alter table study
    change end_date ended_at datetime not null comment '종료 일자';

