create table users(
    id serial primary key,
    login varchar(50),
    password varchar(50) not null,
    role_id int references roles(id)
);

create table roles(
    id serial primary key,
    name varchar(50)
);

create table rules(
    id serial primary key,
    name varchar(50)
);

create table roles_rules(
    id serial primary key,
    role_id int references roles(id),
    rule_id int references rules(id)
);

create table items(
    id serial primary key,
    subject varchar(256),
    text text,
    date_created timestamp default now(),
    date_closed timestamp,
    user_id int references users(id),
    category_id int references categories(id),
    states_id int references states(id)
);

create table comments(
    id serial primary key,
    text text,
    item_id int references items(id),
    date_created timestamp default now()
);

create table attachs(
    id serial primary key,
    path text,
    date_created timestamp default now(),
    item_id int references items(id)
);

create table categories(
    id serial primary key,
    name varchar(50)
);

create table states(
    id serial primary key,
    name varchar(50)
);