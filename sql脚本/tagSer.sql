DROP DATABASE TAG;
CREATE DATABASE TAG;
USE TAG;

CREATE TABLE TAG(
    id int auto_increment,
    name varchar(10),
    used_num int,
    last_active_time date,
    primary key (id)
);