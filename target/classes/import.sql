-- Administradores
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Jose Enrique', 'Limachi Garcia', 'jose.limachi@bancofie.com.bo', '123', 123456, 'ROLE_ADMIN', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Alis Estefani', 'Mollindeo Condori', 'alis.mollinedo@bancofie.com.bo', '123', 234567, 'ROLE_ADMIN', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('David Sebastian', 'Ilario Rivero', 'david.ilario@bancofie.com.bo', '123', 345678, 'ROLE_ADMIN', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Gabriela Belen', 'Yujra Contretas', 'gabriela.yujra@bancofie.com.bo', '123', 456789, 'ROLE_ADMIN', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Shayla Karen', 'Marin Quispe', 'shayla.marin@bancofie.com.bo', '123', 567890, 'ROLE_ADMIN', NOW(), NOW());

-- Encargados
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Encargado1', 'Apellido1', 'encargado1@correo.com', '123', 111111, 'ROLE_MANAGER', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Encargado2', 'Apellido2', 'encargado2@correo.com', '123', 222222, 'ROLE_MANAGER', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Encargado3', 'Apellido3', 'encargado3@correo.com', '123', 333333, 'ROLE_MANAGER', NOW(), NOW());

-- Usuarios
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Usuario1', 'Apellido1', 'usuario1@correo.com', '123', 444444, 'ROLE_USER', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Usuario2', 'Apellido2', 'usuario2@correo.com', '123', 555555, 'ROLE_USER', NOW(), NOW());
insert into user (name, lastname, mail, password, account, userType, create_At, update_At) values ('Usuario3', 'Apellido3', 'usuario3@correo.com', '123', 666666, 'ROLE_USER', NOW(), NOW());

-- Equipo Azul
insert into fienaku (name, code, mount, penitence, timespan, create_At, update_At) values ('Equipo Azul', 'CCX3', 200.0, 20.0, 15, NOW(), NOW());

-- Equipo Magenta
insert into fienaku (name, code, mount, penitence, timespan, create_At, update_At) values ('Equipo Magenta', 'CCX4', 300.0, 30.0, 15, NOW(), NOW());

-- Relacion Equipos - Usuarios
insert into fienaku_User (fienaku_Id, user_Id) values (1, 1);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 2);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 3);

insert into fienaku_User (fienaku_Id, user_Id) values (2, 1);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 2);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 3);

--Pagos

--Cobros
--INSERT INTO CHARGE (fienaku_id, mount_charge, collection_date, qrAccount_image);