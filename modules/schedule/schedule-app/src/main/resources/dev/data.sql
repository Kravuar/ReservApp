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

INSERT INTO service (id, business_id, name, active)
VALUES (1, 1, "cog an bol torture", true),
       (2, 2, "bebr creation", true),
       (3, 2, "akwodawkd", false);

INSERT INTO staff (id, sub, business_id, description, active)
VALUES (1, 'sub1', 1, 'just a bebr', true),
       (2, 'sub2', 2, 'sub2 descr', true),
       (3, 'sub3', 2, 'aajfwnjfa', true),
       (4, 'ownerSub', 2, 'likes to do owner stuff', true);
