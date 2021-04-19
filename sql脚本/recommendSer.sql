DROP DATABASE RECOMMEND;
CREATE DATABASE RECOMMEND;
USE RECOMMEND;

CREATE TABLE SYS_INFO(
    name varchar(20),
    val int,
    primary key (name)
);

CREATE TABLE PAPER_INFO(
    id int,
    tagged_num int,
    uncheck_num int,
    primary key (id)
);

CREATE TABLE PAPER_TAG_RELATION(
    paper_id int,
    tag_name varchar(20),
    degree float,
    tag_num int,
    renew boolean,
    primary key (paper_id, tag_name)
);

CREATE TABLE PAPER_FEATURE(
    paper_id int,
    topic_id int,
    degree float,
    primary key (paper_id, topic_id)
);

CREATE TABLE USER_HISTORY(
    user_id int,
    paper_id int,
    browse_time date,
    uncheck boolean,
    primary key (user_id, paper_id)
);

CREATE TABLE USER_FEATURE(
    user_id int,
    topic_id int,
    degree float,
    last_date date,
    primary key (user_id, topic_id)
);

CREATE TABLE TOPIC_TAG_RELATION(
    topic_id int,
    tag_name varchar(20),
    degree float,
    primary key (topic_id, tag_name)
);

CREATE TABLE TOPIC(
    topic_id int,
    name varchar(20),
    primary key (topic_id)
);

CREATE TABLE USER_FEATURE_INFO(
    user_id int,
    browse_num int,
    renew boolean,
    last_renew_date date,
    primary key (user_id)
);

CREATE TABLE USER_DISLIKE_PAPER(
    user_id int,
    paper_id int,
    primary key (user_id, paper_id)
);

CREATE TABLE USER_DISLIKE_TAG(
    user_id int,
    tag_name varchar(20),
    primary key (user_id, tag_name)
);

CREATE TABLE USER_PAPER_SIMILARITY(
    user_id int,
    paper_id int,
    relateValue float,
    primary key (user_id, paper_id)
);

CREATE TABLE USER_PAPER_SIMILARITY(
    user_id int,
    paper_id int,
    relateValue float,
    primary key (user_id, paper_id)
);

CREATE TABLE USER_SIMILARITY(
    user_id int,
    other_user int,
    relateValue float,
    primary key (user_id, other_user)
);

INSERT INTO SYS_INFO values("user_history_tw", 1);
INSERT INTO SYS_INFO values("paper_tag_num", 5);
INSERT INTO SYS_INFO values("paper_topic_th", 1);
INSERT INTO SYS_INFO values("user_action_date", 2);
INSERT INTO SYS_INFO values("user_action_tw", 1);
INSERT INTO SYS_INFO values("half_life", 1);
INSERT INTO SYS_INFO values("half_life_k", 1);
INSERT INTO SYS_INFO values("paper_uncheck_num", 10);
INSERT INTO SYS_INFO values("paper_tag_th", 3);