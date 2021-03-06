DROP DATABASE PAPER;
CREATE DATABASE PAPER;
USE PAPER;

CREATE TABLE PAPER(
    id int auto_increment,
    title varchar(50),
    abst varchar(1500),
    browse_num int,
    recent_browse_num int,
    primary key (id)
);

CREATE TABLE PAPER_HOT(
    paper_id int,
    ser_num int,
    browse_num int,
    primary key (paper_id, ser_num)
);

CREATE TABLE SYS_INFO(
    name varchar(20),
    val int,
    primary key (name)
);

INSERT INTO SYS_INFO values("paper_hot_tw", 7);
INSERT INTO SYS_INFO values("paper_hot_num", 20);
INSERT INTO SYS_INFO values("current_ser_num", 0);