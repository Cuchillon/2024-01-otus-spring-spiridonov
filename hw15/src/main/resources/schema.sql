create table skeletons (
    id bigserial,
    type varchar(255),
    uuid varchar(255),
    primary key (id)
);

create table covering (
    id bigserial,
    type varchar(255),
    uuid varchar(255),
    primary key (id)
);

create table alloys (
    id bigserial,
    type varchar(255),
    uuid varchar(255),
    primary key (id)
);

create table terminators (
    id bigserial,
    type varchar(255),
    construction varchar(500),
    primary key (id)
);