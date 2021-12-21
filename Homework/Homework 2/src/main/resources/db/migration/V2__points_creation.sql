CREATE TABLE points
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    username     VARCHAR(255)          NULL,
    no_points    INT                   NULL,
    points_added datetime              NULL,
    CONSTRAINT pk_points PRIMARY KEY (id)
);