CREATE TABLE companies (
                           `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
                           `name` varchar(255),
                           `createddate` timestamp
);

INSERT INTO companies (name, createddate) values ('Companie 1', CURDATE());

ALTER TABLE USERS ADD COLUMN companyid BIGINT NULL;

ALTER TABLE users ADD FOREIGN KEY (`companyid`) REFERENCES companies (`Id`);
