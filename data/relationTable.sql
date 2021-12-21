<-- many to one -->
create table job(
    id serial primary key,
    name_job varchar(100)
);

create table people(
    id serial primary key,
    fio varchar(150),
    job_id int references job(id)
);


<-- one to one -->
create table husband(
    id serial primary key,
    fio varchar(150)
);

create table wife(
    id serial primary key,
    fio varchar(150),
    husband_id int references husband(id) unique
);


<-- many to many -->
create table husband(
    id serial primary key,
    fio varchar(150)
);

create table wife(
    id serial primary key,
    fio varchar(150)
);

create table marriage(
    id serial primary key,
    husband_id int references husband(id) unique,
    wife_id int references wife(id) unique
);