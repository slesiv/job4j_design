create database company;

create table persons(
    id serial primary key,
    first_name varchar(50),
    age smallint
);


insert into persons(first_name, age) values ('Григорий', '33');

update persons set first_name='Илья';