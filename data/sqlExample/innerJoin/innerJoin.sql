create table people(
    id serial primary key,
    login varchar(50) not null,
    password varchar(50) not null
);

create table issues(
    id serial primary key,
    subject varchar(256),
    text text,
    date_created timestamp default now(),
    date_closed timestamp,
    people_id int references people(id)
);

insert into people(login, password)
values
('jon', '123456'),
('greg', '987654'),
('marta', '456789');

insert into issues(subject, text, people_id, date_closed)
values
('заявка №1', 'описание заявки №1', 1, '06.05.2021'),
('заявка №2', 'описание заявки №2', 2, '25.04.2021'),
('заявка №3', 'описание заявки №3', 3, '13.09.2021'),
('заявка №3', 'описание заявки №3', 3, null),
('заявка №3', 'описание заявки №3', 3, '18.09.2021');

select * from issues as i
join people as p
on i.people_id = p.id;

select i.date_created, i.date_closed, p.login, i.subject
from issues as i
join people as p
on i.people_id = p.id;

select p.login as Логин, i.subject as "Заголовок задачи", i.date_closed as "Завершение задачи"
from issues as i
join people as p
on i.people_id = p.id;