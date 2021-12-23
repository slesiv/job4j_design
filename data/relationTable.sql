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
create table buyers(
    id serial primary key,
    first varchar(50),
    last varchar(50),
    middle varchar(50),
    age smallint
);

create table dealcards(
    id serial primary key,
    flat_id int,
    amount varchar(14),
    num_contract varchar(30),
    date_contract date
);

create table dealcards_buyers(
    id serial primary key,
    dealcard_id int references dealcards(id),
    buyer_id int references buyers(id)
);