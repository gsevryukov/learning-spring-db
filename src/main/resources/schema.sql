-- author
drop table if exists author;
create table author
(
    id         bigint primary key not null,
    first_name text               not null,
    last_name  text               not null
);
-- genre
drop table if exists genre;
create table genre
(
    id     bigint primary key not null,
    "name" text               not null
);
-- book
drop table if exists book;
create table book
(
    id         bigint primary key not null,
    "name"     text               not null,
    author_ids bigint array       not null,
    genre_ids  bigint array       not null
);
