insert into authors(full_name)
values ('Conan Quasi-Doyle'),
       ('Emmanuelle Quasi-Arsan'),
       ('Bram Quasi-Stoker');

insert into genres(name)
values ('Science fiction'), ('Detective fiction'),
       ('Thriller'), ('Horror'),
       ('Erotic'), ('Porn');

insert into books(title, author_id)
values ('Sherlock Holmes in Space', 1),
       ('Emmanuelle and the Night Chase', 2),
       ('Dracula and the Big Dildo', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 5),
       (3, 4),   (3, 6);