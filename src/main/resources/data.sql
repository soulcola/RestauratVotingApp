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
    ('Суп томатный', '2023-01-01', 150, 1),
    ('Паста Карбонара', '2023-01-01', 300, 1),

    ('Куриный бургер', '2023-01-01', 200, 2),
    ('Стейк Рибай', '2023-01-01', 800, 2),
    ('Салат с киноа', '2023-01-05', 180, 2),
    ('Омлет с ветчиной', '2023-01-05', 160, 2),

    ('Цезарь с креветками', '2023-01-01', 350, 3),
    ('Роллы Филадельфия', '2023-01-01', 500, 3),
    ('Том Ям', '2023-01-05', 450, 3),
    ('Удон с говядиной', '2023-01-05', 380, 3);

INSERT INTO DISH (name, price, restaurant_id)
VALUES
    ('Мясной плов', 250, 1),
    ('Грибная юшка', 200, 1);

INSERT INTO vote (created_at, restaurant_id, user_id)
VALUES ('2023-01-01 10:00:00', 1, 1),
       ('2023-01-01 15:30:00', 2, 2),
       ('2023-01-01 19:45:00', 3, 3),
       ('2023-01-02 10:00:00', 1, 1),
       ('2023-01-02 15:30:00', 2, 2),
       ('2023-01-02 19:45:00', 3, 3);