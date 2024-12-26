CREATE TABLE users
(
    id         BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255)        not null,
    last_name  VARCHAR(255)        not null,
    email      VARCHAR(255) unique not null,
    CONSTRAINT users_id_PK PRIMARY KEY (id)
);