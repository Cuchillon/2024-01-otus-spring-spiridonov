insert into authors(full_name)
values ('Conan Quasi-Doyle'),
       ('Emmanuelle Quasi-Arsan'),
       ('Bram Quasi-Stoker'),
       ('Quasi-author 4'),
       ('Quasi-author 5'),
       ('Quasi-author 6'),
       ('Quasi-author 7'),
       ('Quasi-author 8'),
       ('Quasi-author 9'),
       ('Quasi-author 10');

insert into genres(name)
values ('Science fiction'), ('Detective fiction'),
       ('Thriller'), ('Horror'),
       ('Erotic'), ('Porn');

insert into books(title, author_id)
values ('Sherlock Holmes in Space', 1),
       ('Emmanuelle and the Night Chase', 2),
       ('Dracula and the Big Dildo', 3),
       ('Book 4', 4),
       ('Book 5', 5),
       ('Book 6', 6),
       ('Book 7', 7),
       ('Book 8', 8),
       ('Book 9', 9),
       ('Book 10', 10);

insert into book_comments(text, book_id)
values ('Awesome book, like it', 1),
       ('Holmes in Space is something new', 1),
       ('Emmanuelle is very sexy as always', 2),
       ('Waiting for a new book with Emmanuelle and Emmanuel Macron', 2),
       ('The dildo is OK', 3),
       ('It is a good fiction enough to fall asleep', 3),
       ('First comment 4', 4),
       ('Second comment 4', 4),
       ('First comment 5', 5),
       ('Second comment 5', 5),
       ('First comment 6', 6),
       ('Second comment 6', 6),
       ('First comment 7', 7),
       ('Second comment 7', 7),
       ('First comment 8', 8),
       ('Second comment 8', 8),
       ('First comment 9', 9),
       ('Second comment 9', 9),
       ('First comment 10', 10),
       ('Second comment 10', 10);

insert into books_genres(book_id, genre_id)
values (1, 1),   (1, 2),
       (2, 3),   (2, 5),
       (3, 4),   (3, 6),
       (4, 4),   (5, 5),
       (6, 6),   (7, 1),
       (8, 2),   (8, 3),
       (9, 3),   (10, 4);