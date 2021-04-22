DROP DATABASE ACCOUNT;
CREATE DATABASE ACCOUNT;
USE ACCOUNT;

CREATE TABLE USER_INFO(
    id int auto_increment,
    name varchar(20),
    password varchar(100),
    role varchar(10),
    sub_num int,
    primary key (id)
);

CREATE TABLE USER_SUBSCRIBE(
    user_id int,
    sub_id int,
    primary key (user_id, sub_id)
);

CREATE TABLE USER_DIS(
    user_id int,
    dis_id int,
    primary key (user_id, dis_id)
);

CREATE TABLE USER_SHARE(
    user_id int,
    paper_id int,
    primary key (user_id, paper_id)
);