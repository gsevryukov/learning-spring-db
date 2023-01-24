-- author
insert into author (id, first_name, last_name)
values (100, 'Антон', 'Макаренко');
-- genre
insert into genre (id, "name")
values (1, 'Образовательная книга'),
       (2, 'Художественная книга');
-- book
insert into book (id, "name", author_ids, genre_ids)
values (1, 'Педагогическая поэма', array [100], array [1, 2]);
