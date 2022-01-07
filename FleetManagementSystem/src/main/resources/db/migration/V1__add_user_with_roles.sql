CREATE TABLE users
(
    username VARCHAR(50) NOT NULL,
    password VARCHAR(68) NOT NULL,
    telephoneNumber VARCHAR(10) NOT NULL,
    enabled TINYINT(1) NOT NULL,
    PRIMARY KEY(username)
);

INSERT INTO USERS (USERNAME, PASSWORD, TELEPHONENUMBER, ENABLED) VALUES('admin@admin.com','$2a$10$jskz5pj5RnozRep2526c.uH/Wojdn37F2TuGIN/ssfhPPRHiyGiLe', '0745454545', 1);

CREATE TABLE authorities
(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(68) NOT NULL,
    FOREIGN KEY (username) REFERENCES users(username)
);

INSERT INTO AUTHORITIES(username, authority) VALUES('admin@admin.com','ROLE_ADMIN');
