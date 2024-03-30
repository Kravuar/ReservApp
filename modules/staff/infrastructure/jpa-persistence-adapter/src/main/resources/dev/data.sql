INSERT INTO business (id, owner_sub, active)
VALUES (1, 'bebr1@mail.ru', true),
       (2, 'bebr1@mail.ru', true),
       (3, 'bebr1@mail.ru', true),
       (4, 'bebr1@mail.ru', true),
       (5, 'bebr1@mail.ru', true),
       (6, 'bebr1@mail.ru', true),
       (7, 'bebr1@mail.ru', true),
       (8, 'bebr1@mail.ru', true),
       (9, 'bebr1@mail.ru', true),
       (10, 'bebr1@mail.ru', true),
       (11, 'bebr1@mail.ru', true),
       (12, 'bebr1@mail.ru', true),
       (13, 'bebr1@mail.ru', true),
       (14, 'bebr1@mail.ru', true),
       (15, 'bebr1@mail.ru', false);

INSERT INTO staff_invitation (sub, business_id, created_at, status)
VALUES ('bebr2@mail.ru', 1, '2024-02-25', 2),
       ('bebr1@mail.ru', 1, '2024-02-26', 2),
       ('bebr4@mail.ru', 2, '2024-02-25', 0),
       ('bebr1@mail.ru', 2, '2024-02-25', 1),
       ('bebr3@mail.ru', 2, '2024-02-25', 2);

INSERT INTO staff (sub, business_id, description, active)
VALUES ('bebr1@mail.ru', 2, 'likes to do owner stuff', true),
       ('bebr2@mail.ru', 1, 'just a bebr', true),
       ('bebr3@mail.ru', 2, 'bebr3@mail.ru descr', true),
       ('bebr4@mail.ru', 2, 'aajfwnjfa', true);
