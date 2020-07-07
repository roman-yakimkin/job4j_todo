delete from item;

create table role (
    id      serial primary key,
    name    varchar(200) not null
);

insert into role (id, name) values
    (1, 'admin'),
    (2, 'authenticated user');

create table "user" (
    id          serial primary key,
    name        varchar(200) not null,
    email       varchar(200) not null,
    password    varchar(200) not null,
    role_id     int not null references role(id)
);

alter table item
    add column user_id int not null references "user"(id);