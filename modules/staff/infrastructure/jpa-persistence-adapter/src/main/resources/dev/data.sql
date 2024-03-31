INSERT INTO business (id, owner_sub, active)
VALUES (1, 'realownerowningstuff@mail.ru', true),
       (2, 'realownerowningstuff@mail.ru', true),
       (3, 'realownerowningstuff@mail.ru', true),
       (4, 'realownerowningstuff@mail.ru', true),
       (5, 'realownerowningstuff@mail.ru', true),
       (6, 'realownerowningstuff@mail.ru', true),
       (7, 'realownerowningstuff@mail.ru', true),
       (8, 'realownerowningstuff@mail.ru', true),
       (9, 'realownerowningstuff@mail.ru', true),
       (10, 'realownerowningstuff@mail.ru', true),
       (11, 'realownerowningstuff@mail.ru', true),
       (12, 'realownerowningstuff@mail.ru', true),
       (13, 'realownerowningstuff@mail.ru', true),
       (14, 'realownerowningstuff@mail.ru', true),
       (15, 'realownerowningstuff@mail.ru', false);

INSERT INTO staff_invitation (sub, business_id, created_at, status)
VALUES ('aboba1@mail.ru', 2, '2024-02-25', 1),
       ('aboba2@mail.ru', 2, '2024-02-25', 0),
       ('aboba3@mail.ru', 2, '2024-02-25', 0),
       ('aboba4@mail.ru', 2, '2024-02-25', 0),
       ('aboba1@mail.ru', 1, '2024-02-26', 0);

INSERT INTO staff (sub, business_id, description, active)
VALUES ('aboba1@mail.ru', 1, 'likes to do stuff', true),
       ('aboba2@mail.ru', 2, 'just a aboba', true),
       ('aboba3@mail.ru', 2, 'aboba3@mail.ru descr', true),
       ('aboba4@mail.ru', 2, 'aajfwnjfa', true);
