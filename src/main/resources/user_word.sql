delete from user_word;
alter table user_word AUTO_INCREMENT=1;
insert into user_word select 1, id from word;


