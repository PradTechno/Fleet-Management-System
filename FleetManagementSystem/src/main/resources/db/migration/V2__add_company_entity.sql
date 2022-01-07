CREATE TABLE companies (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `name` varchar(255),
                           `createdDate` timestamp
);

INSERT INTO companies (name, createdDate) values ('Companie 1', CURDATE());

ALTER TABLE USERS ADD COLUMN companyId BIGINT NULL;

ALTER TABLE users ADD FOREIGN KEY (`companyId`) REFERENCES companies (`Id`);
