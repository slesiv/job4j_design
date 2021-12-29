select * from product
join type on product.type_id = type.id
where type.name = 'СЫР';

select * from product
where name like '%мороженое%';

select * from product
where expired_date < current_date;

select * from product
where price = (select max(price) from product);

select t.name, count(p.id)
from product as p
join type as t on p.type_id = t.id
group by t.name;

select * from product as p
join type as t on p.type_id = t.id
where t.name = 'СЫР' OR t.name = 'МОЛОКО';

select t.name
from product as p
join type as t on p.type_id = t.id
group by t.name
having count(p.id) < 10;

select p.name, t.name
from product as p
join type as t on p.type_id = t.id;