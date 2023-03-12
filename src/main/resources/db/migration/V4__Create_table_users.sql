CREATE TABLE users
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    login    VARCHAR(255) NOT NULL,
    name     VARCHAR(255),
    birthday DATE
);