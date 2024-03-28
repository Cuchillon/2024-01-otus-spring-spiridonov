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

insert into book_comments(text, book_id)
values ('Awesome book, like it', 1),
       ('Holmes in Space is something new', 1),
       ('Emmanuelle is very sexy as always', 2),
       ('Waiting for a new book with Emmanuelle and Emmanuel Macron', 2),
       ('The dildo is OK', 3),
       ('It is a good fiction enough to fall asleep', 3);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 5),
       (3, 4),   (3, 6);