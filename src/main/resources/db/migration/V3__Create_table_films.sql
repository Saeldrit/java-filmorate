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