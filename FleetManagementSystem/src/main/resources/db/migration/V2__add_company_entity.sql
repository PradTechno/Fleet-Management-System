CREATE TABLE companies (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `name` varchar(255),
                           `createdDate` timestamp
);

INSERT INTO companies (name, createdDate) values ('Companie 1', CURRENT_TIMESTAMP());

ALTER TABLE USERS ADD COLUMN companyId BIGINT NULL;

ALTER TABLE users ADD FOREIGN KEY (`companyId`) REFERENCES companies (`Id`);

INSERT INTO USERS (companyId, USERNAME, PASSWORD, TELEPHONENUMBER, ENABLED) VALUES(1, 'chitacm00@gmail.com','$2a$10$wz2erLZmHoPCqfVfuBTQFe9bpVh24f3B4Za/NB5lnxkh74AaIdYFW', '0740000000', 1);
INSERT INTO AUTHORITIES(username, authority) VALUES('chitacm00@gmail.com','ROLE_MANAGER');

INSERT INTO USERS (companyId, USERNAME, PASSWORD, TELEPHONENUMBER, ENABLED) VALUES(1, 'marius.chitac@my.fmi.unibuc.ro','$2a$10$yNbpOvKNTFWcEAZwx.3WOOOWH0bYpWX.1O0Ieg5XV88Ak0Holkl5a', '0740000001', 1);
INSERT INTO AUTHORITIES(username, authority) VALUES('marius.chitac@my.fmi.unibuc.ro','ROLE_DRIVER');
