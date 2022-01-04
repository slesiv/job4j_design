create table body(
    id serial primary key,
    type varchar(50)
);

create table engine(
    id serial primary key,
    power int,
    manufacturer varchar(50)
);
create table transmission(
    id serial primary key,
    type varchar(50),
    amount_gears smallint
);

create table car(
    id serial primary key,
    model varchar(50),
    body_id int references body(id) not null,
    engine_id int references engine(id) not null,
    transmission_id int references transmission(id) not null,
    price float
);

insert into body(type)
values
('sedan'),
('coupe'),
('crossover'),
('cabriolet'),
('pickup'),
('limousine'),
('station wagon');

insert into engine(power, manufacturer)
values
(150, 'toyota'),
(250, 'toyota'),
(310, 'toyota'),
(145, 'audi'),
(350, 'audi'),
(130, 'ford'),
(280, 'ford');

insert into transmission(type, amount_gears)
values
('auto', 4),
('auto', 6),
('auto', 7),
('auto', 8),
('manual', 4),
('manual', 5),
('manual', 6);

insert into car(model, body_id, engine_id, transmission_id, price)
values
('Toyota Camry', 1, 2, 3, 35400),
('toyota Rav', 3, 1, 6, 33200),
('Audi A6', 1, 4, 2, 43200),
('Audi R8', 2, 5, 4, 158200),
('Ford RAM', 5, 6, 7, 68200);

select * from car c
left join body b ON c.body_id = b.id
left join engine e ON c.engine_id = e.id
left join transmission t ON c.transmission_id = t.id;