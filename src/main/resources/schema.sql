DROP TABLE IF EXISTS dish CASCADE;
DROP TABLE IF EXISTS restaurant CASCADE;
DROP TABLE IF EXISTS user_role CASCADE;
DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS vote CASCADE;


CREATE TABLE restaurant
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(128) NOT NULL
);

CREATE TABLE dish
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(128)       NOT NULL,
    created_at    DATE DEFAULT now() NOT NULL,
    price         BIGINT             NOT NULL,
    restaurant_id INTEGER            NOT NULL REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(128)            NOT NULL,
    email      VARCHAR(128)            NOT NULL UNIQUE,
    password   VARCHAR(128)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL REFERENCES users ON DELETE CASCADE,
    role    VARCHAR(255),
    PRIMARY KEY (user_id, role)
);

CREATE TABLE vote
(
    id              SERIAL PRIMARY KEY,
    created_at_date date default (cast(now() as date)),
    created_at_time time default (cast(now() as time)),
    restaurant_id   INTEGER,
    user_id         INTEGER
);
CREATE UNIQUE INDEX vote_unique_user_date_idx
    ON vote (user_id, created_at_date);
