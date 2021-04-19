DROP DATABASE TAG;
CREATE DATABASE TAG;
USE TAG;

CREATE TABLE TAG(
    name varchar(20),
    used_num int,
    last_active_time date,
    primary key (name)
);

CREATE TABLE SAME_TAG(
    name varchar(20),
    same_tag_name varchar(20),
    primary key (name, same_tag_name)
);

CREATE TABLE TAG_PAPER(
    paper_id int,
    tag_name varchar(10),
    primary key (paper_id, tag_name)
);