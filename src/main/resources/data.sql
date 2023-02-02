-- author
insert into author (id, first_name, last_name)
values (100, 'Антон', 'Макаренко');
-- genre
insert into genre (id, naming)
values (100, 'Образовательная книга'),
       (101, 'Художественная книга');
-- book
insert into book (id, naming, author_ids, genre_ids)
values (100, 'Педагогическая поэма', array [100], array [100, 101]);
