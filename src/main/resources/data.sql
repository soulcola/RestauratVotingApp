DELETE FROM users;
DELETE FROM user_role;
DELETE FROM restaurant;
DELETE FROM dish;


INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

INSERT INTO RESTAURANT (name)
VALUES ('Restaurant1'),
       ('Restaurant2'),
       ('Restaurant3');

INSERT INTO DISH (name, created_at, price, restaurant_id)
VALUES
    ('Суп томатный', '2023-01-01', 15000, 1),
    ('Паста Карбонара', '2023-01-01', 30000, 1),
    ('Мясной плов', CURRENT_TIMESTAMP, 25013, 1),
    ('Грибная юшка', CURRENT_TIMESTAMP, 20000, 1),

    ('Куриный бургер', '2023-01-01', 20000, 2),
    ('Стейк Рибай', '2023-01-01', 80000, 2),
    ('Салат с киноа', CURRENT_TIMESTAMP, 18000, 2),
    ('Омлет с ветчиной', CURRENT_TIMESTAMP, 16000, 2),

    ('Цезарь с креветками', '2023-01-01', 35000, 3),
    ('Роллы Филадельфия', '2023-01-01', 50000, 3),
    ('Том Ям', CURRENT_TIMESTAMP, 45000, 3),
    ('Удон с говядиной', CURRENT_TIMESTAMP, 38000, 3);

INSERT INTO vote (created_at_date, created_at_time, restaurant_id, user_id)
VALUES ('2023-01-01', '10:00:00', 1, 1),
       ('2023-01-01', '15:30:00', 2, 2),
       ('2023-01-01', '19:45:00', 3, 3);

INSERT INTO vote (restaurant_id, user_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);