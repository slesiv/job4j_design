insert into fauna (name, avg_age, discovery_date)
values
('fish', 15000, null),
('cat', 30000, '01.04.1567'),
('dog', 33000, '21.07.1476');

select * from fauna
where name like '%fish%';

select * from fauna
where avg_age between 10000 and 21000;

select * from fauna
where discovery_date is null;

select * from fauna
where discovery_date < '01.01.1950';