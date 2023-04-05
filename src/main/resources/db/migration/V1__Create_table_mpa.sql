CREATE TABLE mpa
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE genres
(
    id   INTEGER PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE films
(
    id           INTEGER PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(255) NOT NULL,
    release_date DATE         NOT NULL,
    description  TEXT         NOT NULL,
    duration     LONG         NOT NULL,
    rate         INTEGER DEFAULT 0,
    mpa_id       INTEGER      NOT NULL,
    FOREIGN KEY (mpa_id) REFERENCES mpa (id)
);

CREATE TABLE users
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(255) NOT NULL,
    login    VARCHAR(255) NOT NULL,
    CONSTRAINT unique_login UNIQUE (login),
    name     VARCHAR(255),
    birthday DATE
);

CREATE TABLE friends
(
    user_id   INTEGER,
    friend_id INTEGER,
    PRIMARY KEY (user_id, friend_id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (friend_id) REFERENCES users (id)
);

CREATE TABLE film_genre
(
    film_id  INTEGER,
    genre_id INTEGER,
    PRIMARY KEY (film_id, genre_id),
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

CREATE TABLE film_likes
(
    film_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY (film_id, user_id),
    FOREIGN KEY (film_id) REFERENCES films (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);