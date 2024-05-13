create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors(id) on delete cascade,
    primary key (id)
);

create table book_comments (
    id bigserial,
    text varchar(255),
    book_id bigint references books(id) on delete cascade,
    primary key (id)
);

create table books_genres (
    book_id bigint references books(id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (book_id, genre_id)
);

create table users (
    username varchar(255),
    password varchar(255),
    enabled boolean,
    primary key (username)
);

create table authorities (
    username varchar(255),
    authority varchar(255),
    constraint fk_authorities_users foreign key(username) references users(username)
);

create unique index ix_auth_username on authorities (username,authority);