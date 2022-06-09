-- liquibase formatted sql
-- changeset Roman:Init-core-tables


create table if not exists user
(
    id       int          NOT NULL AUTO_INCREMENT,
    login    varchar(250) NOT NULL UNIQUE,
    password varchar(250) NOT NULL,
    role     varchar(30)  NOT NULL default 'USER',
    active   boolean      NOT NULL default true,
    PRIMARY KEY (id)
);

create table if not exists word
(
    id   int          NOT NULL AUTO_INCREMENT,
    word varchar(250) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

create table if not exists user_word
(
    user_id int NOT NULL,
    word_id int NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (word_id) REFERENCES word (id) ON DELETE RESTRICT ON UPDATE CASCADE,
    PRIMARY KEY (user_id, word_id)
);
