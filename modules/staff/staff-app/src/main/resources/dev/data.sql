INSERT INTO business (id, owner_sub, active)
VALUES (1, '1', true),
       (2, '2', true),
       (3, '3', true),
       (4, '4', true),
       (5, '5', true),
       (6, '6', true),
       (7, '7', true),
       (8, '8', true),
       (9, '9', true),
       (10, '1', true),
       (11, '2', true),
       (12, '3', true),
       (13, '4', true),
       (14, '4', true),
       (15, '2', false);

INSERT INTO service (business_id, name, active)
VALUES (1, "cog an bol torture", true),
       (2, "bebr creation", true),
       (3, "akwodawkd", false);

INSERT INTO staff_invitation (sub, business_id, created_at, status)
VALUES ('1', 1, '2024-02-25', 2),
       ('2', 1, '2024-02-26', 2),
       ('3', 2, '2024-02-25', 0),
       ('4', 2, '2024-02-25', 1),
       ('5', 2, '2024-02-25', 2);

INSERT INTO staff (sub, business_id, description, active)
VALUES ('1', 1, 'just a bebr', true),
       ('5', 2, 'very bebr', true);
