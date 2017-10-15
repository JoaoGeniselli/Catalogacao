
select * from role;

select * from user_roles;

-- Autoridades
insert into role(id, role) values(1, 'ADMIN');
insert into role(id, role) values(2, 'USER');

select * from user;

-- Criaçao do Admin 
insert into user(id, enable, name, password, username) values (1, 1, 'Administrador', ***REMOVIDO***, 'admin');
-- Senha crua do admin: ***REMOVIDO***

-- Atribuiçao da autoridade ao admin
insert into user_roles(user_id, roles_id) values (1,1);


