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
CREATE UNIQUE INDEX restaurant_unique_name_idx
    ON restaurant (name);

CREATE TABLE dish
(
    id            SERIAL PRIMARY KEY,
    name          VARCHAR(128)       NOT NULL,
    created_at    DATE DEFAULT now() NOT NULL,
    price         BIGINT             NOT NULL,
    restaurant_id INTEGER            NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(128)            NOT NULL,
    email      VARCHAR(128)            NOT NULL,
    password   VARCHAR(128)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_role
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id              SERIAL PRIMARY KEY,
    created_at_date date default (cast(now() as date)),
    restaurant_id   INTEGER,
    user_id         INTEGER
);
CREATE UNIQUE INDEX vote_unique_user_date_idx
    ON vote (user_id, created_at_date);
