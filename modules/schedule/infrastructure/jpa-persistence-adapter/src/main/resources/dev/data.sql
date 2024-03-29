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

INSERT INTO service (id, business_id, active)
VALUES (1, 1, true),
       (2, 2, true),
       (3, 1, true),
       (4, 3, true),
       (5, 2, true),
       (6, 2, false),
       (7, 2, true),
       (8, 2, true),
       (9, 2, true),
       (10, 2, true),
       (11, 2, true),
       (12, 2, true),
       (13, 2, true),
       (14, 2, true),
       (15, 2, true),
       (16, 2, true),
       (17, 2, true),
       (18, 2, true),
       (19, 3, true),
       (20, 1, true);

INSERT INTO staff (id, business_id, sub, active)
VALUES (1, 1, "bebr2@mail.ru", true),
       (2, 2, "bebr3@mail.ru", true),
       (3, 2, "bebr4@mail.ru", true),
       (4, 2, "bebr1@mail.ru", true);

INSERT INTO schedule (start, end, staff_id, service_id, created_at, active)
VALUES ('2025-03-02', '2025-03-20', 1, 1, '2024-03-02T08:00:00', true),
       ('2025-03-25', '2025-04-15', 1, 1, '2024-03-02T08:00:00', true),
       ('2025-03-03', '2025-03-25', 2, 2, '2024-03-02T08:00:00', true),
       ('2025-04-01', '2025-04-20', 2, 2, '2024-03-02T08:00:00', true);

INSERT INTO schedule_pattern (schedule_id, repeat_days, pause_days)
VALUES (1, 3, 2),
       (1, 3, 5),
       (2, 2, 2),
       (2, 2, 5),
       (3, 2, 2),
       (3, 2, 4),
       (4, 3, 3),
       (4, 4, 3);

INSERT INTO schedule_exception_day (date, staff_id, service_id)
VALUES ('2025-03-09', 1, 1),
       ('2025-03-13', 2, 2);

INSERT INTO pattern_slots (schedule_pattern_id, start, end, cost, max_reservations)
VALUES (1, '08:00:00', '09:00:00', 20.0, 1),
       (1, '09:30:00', '10:30:00', 20.0, 1),
       (1, '11:00:00', '12:00:00', 20.0, 1),
       (1, '12:00:00', '13:00:00', 20.0, 1),
       (1, '14:00:00', '15:00:00', 20.0, 1),
       (1, '16:00:00', '17:00:00', 20.0, 1),
       (2, '08:30:00', '09:30:00', 25.0, 1),
       (2, '10:00:00', '11:00:00', 25.0, 1),
       (2, '11:30:00', '12:30:00', 25.0, 1),
       (2, '13:00:00', '14:00:00', 25.0, 1),
       (2, '14:30:00', '15:30:00', 25.0, 1),
       (2, '16:30:00', '17:30:00', 25.0, 1),
       (3, '08:15:00', '09:15:00', 18.0, 1),
       (3, '10:15:00', '11:15:00', 18.0, 1),
       (3, '12:15:00', '13:15:00', 18.0, 1),
       (3, '13:45:00', '14:45:00', 18.0, 1),
       (3, '15:15:00', '16:15:00', 18.0, 1),
       (3, '16:45:00', '17:45:00', 18.0, 1),
       (4, '08:45:00', '09:45:00', 22.0, 1),
       (4, '10:45:00', '11:45:00', 22.0, 1),
       (4, '12:45:00', '13:45:00', 22.0, 1),
       (4, '14:15:00', '15:15:00', 22.0, 1),
       (4, '15:45:00', '16:45:00', 22.0, 1),
       (4, '17:15:00', '18:15:00', 22.0, 1),
       (5, '08:20:00', '09:20:00', 21.0, 1),
       (5, '09:50:00', '10:50:00', 21.0, 1),
       (5, '11:20:00', '12:20:00', 21.0, 1),
       (5, '13:20:00', '14:20:00', 21.0, 1),
       (5, '14:50:00', '15:50:00', 21.0, 1),
       (5, '16:20:00', '17:20:00', 21.0, 1),
       (6, '08:10:00', '09:10:00', 19.0, 1),
       (6, '09:40:00', '10:40:00', 19.0, 1),
       (6, '11:10:00', '12:10:00', 19.0, 1),
       (6, '12:40:00', '13:40:00', 19.0, 1),
       (6, '14:10:00', '15:10:00', 19.0, 1),
       (6, '15:40:00', '16:40:00', 19.0, 1),
       (7, '08:55:00', '09:55:00', 23.0, 3),
       (7, '10:55:00', '11:55:00', 23.0, 3),
       (7, '12:55:00', '13:55:00', 23.0, 3),
       (7, '14:25:00', '15:25:00', 23.0, 3),
       (7, '15:55:00', '16:55:00', 23.0, 3),
       (7, '17:25:00', '18:25:00', 23.0, 3),
       (8, '08:25:00', '09:25:00', 24.0, 3),
       (8, '09:55:00', '10:55:00', 24.0, 3),
       (8, '11:25:00', '12:25:00', 24.0, 3),
       (8, '13:25:00', '14:25:00', 24.0, 3),
       (8, '14:55:00', '15:55:00', 24.0, 3),
       (8, '16:25:00', '17:25:00', 24.0, 3);

INSERT INTO exception_days_slots (schedule_exception_day_id, start, end, cost, max_reservations)
VALUES (1, '08:25:00', '09:25:00', 50.0, 1),
       (1, '09:55:00', '10:55:00', 50.0, 1),
       (1, '11:25:00', '12:25:00', 50.0, 1),
       (2, '13:25:00', '14:25:00', 100.0, 1),
       (2, '14:55:00', '15:55:00', 100.0, 1),
       (2, '16:25:00', '17:25:00', 100.0, 1);

INSERT INTO reservation (date, start, end, client_sub, staff_id, service_id, active, created_at)
VALUES ('2025-03-03', '09:30:00', '10:30:00', 'client@maile.ru', 1, 1, true, '2024-03-02T08:00:00'),
       ('2025-03-08', '11:30:00', '12:30:00', 'client@maile.ru', 1, 1, true, '2024-03-02T08:00:00');
