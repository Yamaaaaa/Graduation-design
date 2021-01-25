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

CREATE TABLE TAG_PAPER(
    id int auto_increment,
    paper_id int,
    tag_id int,
    tag_num int,
    primary key (id),
    foreign key (paper_id) REFERENCES PAPER_INFO(id)
);

CREATE TABLE PAPER_TAG_RELATION(
    paper_id int,
    tag_id int,
    degree float,
    primary key (paper_id, tag_id),
    foreign key (paper_id) REFERENCES PAPER_INFO(id)
);

CREATE TABLE PAPER_FEATURE(
    paper_id int,
    topic_id int,
    degree float,
    primary key (paper_id, topic_id),
    foreign key (paper_id) REFERENCES PAPER_INFO(id),
    foreign key (topic_id) REFERENCES TOPIC_TAG_RELATION(topic_id)
);

CREATE TABLE USER_HISTORY(
    user_id int,
    paper_id int,
    browse_time date,
    uncheck boolean,
    primary key (user_id, paper_id),
    foreign key (paper_id) REFERENCES PAPER_INFO(id),
);

CREATE TABLE USER_FEATURE(
    user_id int,
    topic_id int,
    degree float,
    last_date date,
    primary key (user_id, topic_id),
    foreign key (topic_id) REFERENCES TOPIC_TAG_RELATION(topic_id)
);

CREATE TABLE TOPIC_TAG_RELATION(
    topic_id int,
    tag_id int,
    degree float,
    primary key (topic_id, tag_id)
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
INSERT INTO SYS_INFO values("user_history_tw", 1);
INSERT INTO SYS_INFO values("paper_tag_num", 5);
INSERT INTO SYS_INFO values("paper_topic_th", 1);
INSERT INTO SYS_INFO values("user_action_date", 2);
INSERT INTO SYS_INFO values("user_action_tw", 1);
INSERT INTO SYS_INFO values("half_life", 1);
INSERT INTO SYS_INFO values("half_life_k", 1);