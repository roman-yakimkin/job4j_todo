alter table "user"
    add constraint unique_email_constraint UNIQUE (email);

insert into "user" (id, name, email, password, role_id) values
    (1, 'admin', 'admin@localhost', '123456', 1);