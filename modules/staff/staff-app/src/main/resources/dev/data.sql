INSERT INTO business (id, owner_sub, active)
VALUES (1, 'ownerSub', true),
       (2, 'ownerSub', true),
       (3, 'ownerSub', true),
       (4, 'ownerSub', true),
       (5, 'ownerSub', true),
       (6, 'ownerSub', true),
       (7, 'ownerSub', true),
       (8, 'ownerSub', true),
       (9, 'ownerSub', true),
       (10, 'ownerSub', true),
       (11, 'ownerSub', true),
       (12, 'ownerSub', true),
       (13, 'ownerSub', true),
       (14, 'ownerSub', true),
       (15, 'ownerSub', false);

INSERT INTO service (business_id, name, active)
VALUES (1, "cog an bol torture", true),
       (2, "bebr creation", true),
       (2, "akwodawkd", false);

INSERT INTO staff_invitation (sub, business_id, created_at, status)
VALUES ('sub1', 1, '2024-02-25', 2),
       ('ownerSub', 1, '2024-02-26', 2),
       ('sub3', 2, '2024-02-25', 0),
       ('ownerSub', 2, '2024-02-25', 1),
       ('sub2', 2, '2024-02-25', 2);

INSERT INTO staff (sub, business_id, description, active)
VALUES ('sub1', 1, 'just a bebr', true),
       ('sub2', 2, 'sub2 descr', true),
       ('sub3', 2, 'aajfwnjfa', true),
       ('ownerSub', 2, 'likes to do owner stuff', true);
