drop table if exists item;

create table item (
    id          serial primary key,
    descr       text not null,
    created     timestamp default now(),
    done        boolean not null default false
);