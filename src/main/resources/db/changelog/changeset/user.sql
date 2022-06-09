-- liquibase formatted sql
-- changeset Roman:populate-users-table

alter table user AUTO_INCREMENT=1;
insert into user(login, password, role) values ("client", "$2a$12$lyn3jY6xyPn421Q7Ioobh.z9AA3OZQRIpO9azQx5/V8nP7Zvdk3tK", "APP");
insert into user(login, password, role) values("ADMIN", "$2a$12$IQY9R9P4q43k1F8wpvRNWu./52xBP2fUMORUZNZ6/71q1rEB9FNLq", "ADMIN");
insert into user(login, password) values("test", "$2a$12$WQtte8UvG.Yg1oDWZ.dG0uRGnd.L0e6utXwHWPxPIhxfERh20PLlW");

