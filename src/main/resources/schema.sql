-- author
drop table if exists book_author;
drop table if exists book_genre;
drop table if exists author;
drop table if exists comment;
create table author
(
    id         bigserial not null,
    first_name varchar   not null,
    last_name  varchar   not null,
    primary key (id)
);
-- genre
drop table if exists genre;
create table genre
(
    id     bigserial not null,
    "name" varchar   not null,
    primary key (id)
);
-- comment
drop table if exists comment;
create table comment
(
    id     bigserial not null,
    "text" varchar   not null,
    primary key (id)
);
-- book
drop table if exists book;
create table book
(
    id     bigserial not null,
    "name" varchar   not null,
    primary key (id)
);
-- book_author
create table book_author
(
    book_id   bigint references book (id)   not null,
    author_id bigint references author (id) not null,
    primary key (book_id, author_id)
);
-- book_genre
create table book_genre
(
    book_id  bigint references book (id)  not null,
    genre_id bigint references genre (id) not null,
    primary key (book_id, genre_id)
);-- book_comment
create table book_comment
(
    book_id    bigint references book (id)    not null,
    comment_id bigint references comment (id) not null,
    primary key (book_id, comment_id)
);
