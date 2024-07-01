--Administradores
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre', 'apellido', 'Administrador', '23', '221456', 'admin@correo.com', 562115, 'ROLE_ADMIN', 'Admin', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());

--Encargados
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('Jose Enrique', 'Limachi Garcia', 'Pasante', '23', '123456', 'jose.limachi@bancofie.com.bo', 153218, 'ROLE_MANAGER', 'Encargado 1', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre', 'apellido', 'Pasante', '23', '123426', 'user@correo.com', 153248, 'ROLE_MANAGER', 'Encargado 2', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());

-- Usuarios
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('Alis Estefani', 'Mollindeo Condori', 'Pasante', '23', '654321', 'alis.mollinedo@bancofie.com.bo', 123456, 'ROLE_USER', 'Alis', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('David Sebastian', 'Ilario Rivero', 'Pasante', '23', '789456', 'david.ilario@bancofie.com.bo', 321654, 'ROLE_USER', 'David', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('Gabriela Belen', 'Yujra Contretas', 'Pasante', '23', '456789', 'gabriela.yujra@bancofie.com.bo', 2456142, 'ROLE_USER', 'Gabriela', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('Shayla Karen', 'Marin Quispe', 'Pasante', '23', '321654', 'shayla.marin@bancofie.com.bo', 2345678, 'ROLE_USER', 'Shayla', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());

insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre1', 'apellido1', 'Pasante', '23', '3216124', 'user1@correo.com', 2315678, 'ROLE_USER', 'User 1', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre2', 'apellido2', 'Pasante', '23', '3216224', 'user2@correo.com', 2375678, 'ROLE_USER', 'User 2', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre3', 'apellido3', 'Pasante', '23', '3216324', 'user3@correo.com', 2385678, 'ROLE_USER', 'User 3', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre4', 'apellido4', 'Pasante', '23', '3216424', 'user4@correo.com', 2395678, 'ROLE_USER', 'User 4', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());
insert into user (name, lastname, job, floor, phone, mail, account, userType, username, password, create_At) values ('nombre5', 'apellido5', 'Pasante', '23', '3216524', 'user5@correo.com', 2405678, 'ROLE_USER', 'User 5', '$2a$10$mfIRU2sow.VMXSSrOxuM2uzIEEi9ignZ1oUPzfsh5MS.PyeerVPIm', NOW());

-- Equipo Azul
insert into fienaku (name, code, mount, penitence, timespan, create_At, update_At) values ('Equipo Azul', 'CCX3', 200.0, 20.0, 15, NOW(), NOW());

-- Equipo Magenta
insert into fienaku (name, code, mount, penitence, timespan, create_At, update_At) values ('Equipo Magenta', 'CCX4', 300.0, 30.0, 31, NOW(), NOW());

-- Relacion Equipos - Usuarios
insert into fienaku_User (fienaku_Id, user_Id) values (1, 2);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 4);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 5);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 6);
insert into fienaku_User (fienaku_Id, user_Id) values (1, 7);

insert into fienaku_User (fienaku_Id, user_Id) values (2, 3);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 8);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 9);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 10);
insert into fienaku_User (fienaku_Id, user_Id) values (2, 11);

--Pagos

--INSERT INTO CHARGE (fienaku_id, mount_charge, collection_date, qrAccount_image);