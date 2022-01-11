CREATE TABLE cars
(
    id            BIGINT PRIMARY KEY AUTO_INCREMENT,
    companyId     BIGINT       NOT NULL,
    brand         varchar(255) NOT NULL,
    model         varchar(255) NOT NULL,
    modelYear     integer      NOT NULL,
    tankCapacity  integer      NOT NULL,
    licenseNeeded varchar(10)  NOT NULL,
    createdDate   timestamp    NOT NULL,
    createdBy     varchar(255) NOT NULL
);

CREATE TABLE carMaintenances
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    carId       BIGINT       NOT NULL,
    numberOfKm  integer,
    setDate     DATE,
    checked     BIT          NOT NULL default 0,
    createdDate timestamp    NOT NULL,
    createdBy   varchar(255) NOT NULL
);

CREATE TABLE drivingCategories
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    driverId       varchar(50) NOT NULL,
    category       VARCHAR(10) NOT NULL,
    issueDate      timestamp   NOT NULL,
    expirationDate timestamp   NOT NULL
);

CREATE TABLE transports
(
    id                 BIGINT PRIMARY KEY AUTO_INCREMENT,
    driverId           varchar(50)  NOT NULL,
    carId              BIGINT       NOT NULL,
    originAddress      varchar(255) NOT NULL,
    destinationAddress varchar(255) NOT NULL,
    startDate          timestamp    NOT NULL,
    finishDate         timestamp,
    status             VARCHAR(20)  NOT NULL
);

CREATE TABLE transportActions
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    transportId BIGINT      not null,
    type        VARCHAR(15) NOT NULL,
    currentFuel decimal     NOT NULL,
    numberOfKm  INTEGER     NOT NULL,
    createdDate timestamp   not null
);

CREATE TABLE routes
(
    id            BIGINT PRIMARY KEY,
    totalDuration BIGINT,
    totalDistance BIGINT
);

ALTER TABLE cars
    ADD FOREIGN KEY (companyId) REFERENCES companies (id);

ALTER TABLE carMaintenances
    ADD FOREIGN KEY (carId) REFERENCES cars (id);

ALTER TABLE drivingCategories
    ADD FOREIGN KEY (driverId) REFERENCES users (username);

ALTER TABLE transports
    ADD FOREIGN KEY (driverId) REFERENCES users (username);

ALTER TABLE transports
    ADD FOREIGN KEY (carId) REFERENCES cars (id);

ALTER TABLE transportActions
    ADD FOREIGN KEY (transportId) REFERENCES transports (id);

ALTER TABLE routes
    ADD FOREIGN KEY (id) REFERENCES transports (id);


INSERT INTO cars (companyId, brand, model, modelYear, tankCapacity, licenseNeeded, createdDate, createdBy)
values (1, 'MAN', 'TGA 18.430', 2004, 910, 'C', CURRENT_TIMESTAMP(), 'chitacm00@gmail.com');

INSERT INTO carMaintenances (carId, numberOfKm, setDate, checked, createdDate, createdBy)
VALUES (LAST_INSERT_ID(), 100000, null, 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), 150000, null, 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), null, '2022-01-02', 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), null, '2022-05-30', 0, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com');


INSERT INTO cars (companyId, brand, model, modelYear, tankCapacity, licenseNeeded, createdDate, createdBy)
values (1, 'MAN', 'TGA 26.440', 2012, 400, 'C', CURRENT_TIMESTAMP(), 'chitacm00@gmail.com');

INSERT INTO carMaintenances (carId, numberOfKm, setDate, checked, createdDate, createdBy)
VALUES (LAST_INSERT_ID(), 300000, null, 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), null, '2021-10-20', 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), null, '2022-01-02', 1, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com'),
       (LAST_INSERT_ID(), null, '2022-03-21', 0, CURRENT_TIMESTAMP(), 'chitacm00@gmail.com');