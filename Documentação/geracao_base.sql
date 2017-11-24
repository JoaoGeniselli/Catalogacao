
-- Criação da base catalogacao
CREATE DATABASE catalogacao;

-- Criação do usuário para ser utilizado no sistema web
CREATE USER 'catalogacao_web'@'%' IDENTIFIED BY 'Cortadeiras7102';

-- Atribuicao de privilegios para o usuario do sistema web
GRANT ALL PRIVILEGES  ON catalogacao.* TO 'catalogacao_web'@'%';

-- Seleçao da nova base
use catalogacao;

-- ATENÇÃO: Os códigos a seguir para a criação inicial da base foram gerados
-- pelo HIBERNATE, quaisquer alterações em nomes de campos ou tabelas devem ser
-- feitas com muita cautela, pois essa nomenclatura é mapeada automaticamente
-- nas entidades da aplicação web.

-- Drop de chaves estrangeiras, nao e necessario para o primeiro deploy, so estao listados aqui para ajudar em possiveis limpezas da base

-- alter table ant drop foreign key FKei6ujiwlchd5ixaov3myb79m5;
-- alter table ant drop foreign key FK2nh295odwwfifg0ap5laiojxy;
-- alter table ant_photos drop foreign key FKedtiu9lups68bhcwg2in3gej3;
-- alter table ant_photos drop foreign key FKst8x94y8k7o9xhco0y7mghq53;
-- alter table ant_nest drop foreign key FKkha0c228b26yey6g1ufrhakyq;
-- alter table ant_nest_photos drop foreign key FK2v632td89isegy46kwd9ido0b;
-- alter table ant_nest_photos drop foreign key FKb1f3ho3lu8y7jl8nbuo2lxg90;
-- alter table city drop foreign key FKl8nfn9lq82tvw76mrew3xj3c8;
-- alter table country_state drop foreign key FKps6hoo8txvda3l5ix2lh92swu;
-- alter table data_update_visit drop foreign key FKix99kxdrvwcxf88vi2vb3jvk3;
-- alter table data_update_visit drop foreign key FKsjrw3iwpch034yu4klhrev45m;
-- alter table data_update_visit drop foreign key FK1jn6er4rhp58d09i1yqicajqv;
-- alter table data_update_visit drop foreign key FKdesidtjwspndqq0u6yvyuylcd;
-- alter table data_update_visit_photos drop foreign key FK3imagvus42t5yq3s9rdow1aey;
-- alter table data_update_visit_photos drop foreign key FKcenl3kps6ignrati939x721rr;
-- alter table mobile_token drop foreign key FKmg5xhnenvbsjxjmhbiebpf2pf;
-- alter table user_roles drop foreign key FKj9553ass9uctjrmh0gkqsmv0d;
-- alter table user_roles drop foreign key FK55itppkw3i07do3h7qoclqd4k;

-- Drop de possiveis tabelas existentes
drop table if exists ant;
drop table if exists ant_photos;
drop table if exists ant_nest;
drop table if exists ant_nest_photos;
drop table if exists city;
drop table if exists coordinate;
drop table if exists country;
drop table if exists country_state;
drop table if exists data_update_visit;
drop table if exists data_update_visit_photos;
drop table if exists mobile_token;
drop table if exists photo;
drop table if exists role;
drop table if exists user;
drop table if exists user_roles;

-- Criaçao das tabelas
create table ant (id bigint not null auto_increment, ant_family varchar(255), ant_genus varchar(255), ant_order varchar(255), ant_species varchar(255), ant_subfamily varchar(255), ant_subgenus varchar(255), name varchar(255), notes varchar(255), register_date date, ant_nest_nest_id bigint, visit_id bigint, primary key (id));
create table ant_photos (ant_id bigint not null, photos_id bigint not null);
create table ant_nest (nest_id bigint not null auto_increment, active bit, address varchar(255), name varchar(255), register_date date, vegetation varchar(255), city_id bigint, primary key (nest_id));
create table ant_nest_photos (ant_nest_nest_id bigint not null, photos_id bigint not null);
create table city (id bigint not null auto_increment, name varchar(255), state_id bigint, primary key (id));
create table coordinate (id bigint not null auto_increment, latitude double precision not null, longitude double precision not null, primary key (id));
create table country (id bigint not null auto_increment, name varchar(255), primary key (id));
create table country_state (id bigint not null auto_increment, initials varchar(255), name varchar(255), country_id bigint, primary key (id));
create table data_update_visit (id bigint not null auto_increment, collection_date date, notes varchar(255), register_date date, collector_id bigint, nest_nest_id bigint, new_begining_point_id bigint, new_ending_point_id bigint, primary key (id));
create table data_update_visit_photos (data_update_visit_id bigint not null, photos_id bigint not null);
create table mobile_token (id bigint not null auto_increment, token varchar(255), user_id bigint, primary key (id));
create table photo (id bigint not null auto_increment, description varchar(255), filepath varchar(255), register_date date, primary key (id));
create table role (id bigint not null auto_increment, role varchar(255), primary key (id));
create table user (id bigint not null auto_increment, enable bit, name varchar(255), password varchar(255), username varchar(255), primary key (id));
create table user_roles (user_id bigint not null, roles_id bigint not null);

-- Criaçao das constraints de relacionamento
alter table ant_photos add constraint UK_hslkd1fx647xvby1qvfai4rie unique (photos_id);
alter table ant_nest_photos add constraint UK_rc7at5ci2mfhhwk8lbytdg82d unique (photos_id);
alter table data_update_visit_photos add constraint UK_milbb6o4dsfsyb5y9ta6yxxdm unique (photos_id);
alter table ant add constraint FKei6ujiwlchd5ixaov3myb79m5 foreign key (ant_nest_nest_id) references ant_nest (nest_id);
alter table ant add constraint FK2nh295odwwfifg0ap5laiojxy foreign key (visit_id) references data_update_visit (id);
alter table ant_photos add constraint FKedtiu9lups68bhcwg2in3gej3 foreign key (photos_id) references photo (id);
alter table ant_photos add constraint FKst8x94y8k7o9xhco0y7mghq53 foreign key (ant_id) references ant (id);
alter table ant_nest add constraint FKkha0c228b26yey6g1ufrhakyq foreign key (city_id) references city (id);
alter table ant_nest_photos add constraint FK2v632td89isegy46kwd9ido0b foreign key (photos_id) references photo (id);
alter table ant_nest_photos add constraint FKb1f3ho3lu8y7jl8nbuo2lxg90 foreign key (ant_nest_nest_id) references ant_nest (nest_id);
alter table city add constraint FKl8nfn9lq82tvw76mrew3xj3c8 foreign key (state_id) references country_state (id);
alter table country_state add constraint FKps6hoo8txvda3l5ix2lh92swu foreign key (country_id) references country (id);
alter table data_update_visit add constraint FKix99kxdrvwcxf88vi2vb3jvk3 foreign key (collector_id) references user (id);
alter table data_update_visit add constraint FKsjrw3iwpch034yu4klhrev45m foreign key (nest_nest_id) references ant_nest (nest_id);
alter table data_update_visit add constraint FK1jn6er4rhp58d09i1yqicajqv foreign key (new_begining_point_id) references coordinate (id);
alter table data_update_visit add constraint FKdesidtjwspndqq0u6yvyuylcd foreign key (new_ending_point_id) references coordinate (id);
alter table data_update_visit_photos add constraint FK3imagvus42t5yq3s9rdow1aey foreign key (photos_id) references photo (id);
alter table data_update_visit_photos add constraint FKcenl3kps6ignrati939x721rr foreign key (data_update_visit_id) references data_update_visit (id);
alter table mobile_token add constraint FKmg5xhnenvbsjxjmhbiebpf2pf foreign key (user_id) references user (id);
alter table user_roles add constraint FKj9553ass9uctjrmh0gkqsmv0d foreign key (roles_id) references role (id);
alter table user_roles add constraint FK55itppkw3i07do3h7qoclqd4k foreign key (user_id) references user (id);

***REMOVED***
***REMOVED***
***REMOVED***

***REMOVED***

***REMOVED***
insert into user(id, enable, name, password, username) values (1, 1, 'Administrador', ***REMOVED***, 'admin');
***REMOVED***

***REMOVED***
***REMOVED***

-- Checagem da criacao do administrador
select u.*, r.role from user u join user_roles ur on (ur.user_id = u.id) join role r on (r.id = ur.roles_id);
