
-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
CREATE TABLE flyway_test (
  next_val int DEFAULT NULL
);

insert into hibernate_sequence values (4);
insert into hibernate_sequence values (5);

insert into flyway_test values (1);
insert into flyway_test values (2);
insert into flyway_test values (3);