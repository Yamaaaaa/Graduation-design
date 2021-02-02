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

CREATE TABLE SAME_TAG(
    name varchar(10),
    same_tag_id int,
    primary key (name, id)
);

CREATE TABLE TAG_PAPER(
    paper_id int,
    tag_name varchar(10),
    primary key (paper_id, tag_name)
);