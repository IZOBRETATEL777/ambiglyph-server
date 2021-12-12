delete from user;
alter table user AUTO_INCREMENT=1;
insert into user(login, password, role) values("ADNIN", "$2a$12$TcYwzQtxCV21VIOTdRpyPuXGsPPvTdMb7y174BF/4wUVB91lG4Ai6", "ADNIN");
insert into user(login, password) values("test", "$2a$12$vui6SH5mgtGSHOsE/5KxZ.LHuSP3t97lrcgG639Q6nWjfl89QImGW");

