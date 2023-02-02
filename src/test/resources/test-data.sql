-- author
insert into author (id, first_name, last_name)
values (100, 'Антон', 'Макаренко');
-- genre
insert into genre (id, naming)
values (100, 'Образовательная книга'),
       (101, 'Художественная книга');
-- book
insert into book (id, naming)
values (100, 'Педагогическая поэма');
-- book_author
insert into book_author(book_id, author_id) values (100, 100);
-- book_genre
insert into book_genre(book_id, genre_id) values (100, 100), (100, 101);
