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

INSERT INTO schedule (service_id, staff_id, day_of_week, valid_from, disabled_at, working_hours)
VALUES
    (1, 1, 'MONDAY', '2024-02-25', NULL, '[{"start":"08:00:00","end":"17:00:00","cost":50.0},{"start":"10:00:00","end":"18:00:00","cost":60.0}]'),
    (2, 5, 'TUESDAY', '2024-02-26', NULL, '[{"start":"09:00:00","end":"16:00:00","cost":55.0},{"start":"11:00:00","end":"17:00:00","cost":65.0}]'),
    (3, 2, 'WEDNESDAY', '2024-02-27', NULL, '[{"start":"08:30:00","end":"16:30:00","cost":45.0},{"start":"09:30:00","end":"17:30:00","cost":55.0}]'),
    (1, 1, 'THURSDAY', '2024-02-28', NULL, '[{"start":"08:00:00","end":"17:00:00","cost":50.0},{"start":"10:00:00","end":"18:00:00","cost":60.0}]'),
    (2, 5, 'FRIDAY', '2024-02-29', NULL, '[{"start":"09:00:00","end":"16:00:00","cost":55.0},{"start":"11:00:00","end":"17:00:00","cost":65.0}]');

