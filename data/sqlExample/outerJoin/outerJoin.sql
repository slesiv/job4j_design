create table departments(
    id serial primary key,
    name varchar(100),
    max_count_emploers int,
);

create table emploers(
    id serial primary key,
    last varchar(50),
    first varchar(50),
    salary float,
    id_department int references departments(id),
    age smallint
);

insert into departments(name, max_count_emploers)
values
('security', 100),
('finance', 200),
('it', 400),
('media', 50),
('management', 50);

insert into emploers(last, first, salary, id_department, age)
values
('Петров', 'Иван', 2000, 1, 27),
('Севостянов', 'Глеб', 2100, 1, 29),
('Власов', 'Илья', 2060, 1, 26),
('Мацько', 'Дмитрий', 2500, 1, 30),
('Емельянов', 'Роман', 2300, 1, 32),
('Багрецова', 'Мария', 2400, 2, 35),
('Грошева', 'Анна', 2430, 2, 30),
('Антрунов', 'Иван', 2170, 2, 33),
('Коткин', 'Антон', 3300, 2, 42),
('Фролов', 'Марк', 2890, 2, 36),
('Потапов', 'Федр', 2450, 2, 31),
('Колесников', 'Глеб', 1950, 3, 31),
('Артемидов', 'Николай', 2950, 3, 34),
('Фетучев', 'Амир', 2380, 3, 28),
('Амоян', 'Галина', 2780, 3, 31),
('Рыхлов', 'Петр', 3890, 5, 39),
('Тарасов', 'Андрей', 4100, 5, 37);

select * from emploers e left join departments d ON e.id_department = d.id;

select * from emploers e right join departments d ON e.id_department = d.id;

select * from emploers cross join departments;

select d.id, d.name from departments d left join emploers e ON d.id = e.id_department where e.id_department is null;

select e.id, e.last, e.first, d.name from emploers e left join departments d ON e.id_department = d.id;
select e.id, e.last, e.first, d.name from departments d right join emploers e ON d.id = e.id_department;

create table teens(
    name varchar(100),
    gender smallint
);

insert into teens(name, gender)
values
('Anna', 1),
('Jon', 2),
('Gleb', 2),
('Maria', 1),
('Sofa', 1),
('Jake', 3);

select t1.name, t1.gender, t2.gender, t2.name from teens t1 cross join teens t2 where t1.gender != t2.gender;