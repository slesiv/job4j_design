insert into users(login, password, role_id)
values
('jon', '123456', 1),
('greg', '987654', 2),
('marta', '456789', 3);

insert into roles(name)
values
('admin'),
('junior'),
('employee');

insert into rules(name)
values
('users create'),
('users update'),
('users delete'),
('items create'),
('items update'),
('items delete'),
('other info create'),
('other info update'),
('other info delete');

insert into roles_rules(role_id, rule_id)
values
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5),
(1, 6),
(1, 7),
(1, 8),
(1, 9),
(2, 4),
(2, 5),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(3, 9);

insert into items(subject, text, user_id, category_id, states_id)
values
('заявка №1', 'описание заявки №1', 4, 1, 1),
('заявка №2', 'описание заявки №2', 5, 2, 2),
('заявка №3', 'описание заявки №3', 6, 3, 3);

insert into comments(text, item_id)
values
('здесь должен быть комментарий к заявке №1', 4),
('здесь должен быть комментарий к заявке №2', 5),
('здесь должен быть комментарий к заявке №3', 6);

insert into attachs(path, item_id)
values
('screenshot1.png', 4),
('screenshot2.png', 4),
('screenshot3.png', 4);

insert into categories(name)
values
('get'),
('send'),
('destroy');

insert into states(name)
values
('registration'),
('in work'),
('finished');