insert into devices (name, price)
values
('phone', 1000),
('TV', 1500),
('ipod', 300),
('notebook', 1300),
('PC', 1100),
('headphones', 100.50),
('conditioner', 325.30),
('toaster', 56.40),
('washingMmachine', 350.80);

insert into people (name)
values
('Ivan'),
('Jon'),
('Maria'),
('Gleb'),
('Anna'),
('Max');

insert into devices_people (device_id, people_id)
values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(2, 1),
(2, 3),
(2, 5),
(3, 2),
(3, 4),
(3, 6),
(4, 1),
(4, 2),
(4, 3),
(5, 4),
(5, 5),
(5, 6),
(6, 1),
(6, 2),
(6, 4),
(7, 3),
(7, 5),
(7, 6),
(8, 6),
(8, 5),
(9, 6);

select avg(price) from devices;

select p.name, avg(d.price)
from devices_people as dp
join devices as d on dp.device_id = d.id
join people as p on dp.people_id = p.id
group by p.name;

select p.name, avg(d.price)
from devices_people as dp
join devices as d on dp.device_id = d.id
join people as p on dp.people_id = p.id
group by p.name
having avg(d.price) > 700;