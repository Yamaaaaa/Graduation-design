DROP DATABASE ACCOUNT;
CREATE DATABASE ACCOUNT;
USE ACCOUNT;

CREATE TABLE USER_INFO(
    id int auto_increment,
    name varchar(20),
    password varchar(20),
    role varchar(10),
    primary key (id)
);