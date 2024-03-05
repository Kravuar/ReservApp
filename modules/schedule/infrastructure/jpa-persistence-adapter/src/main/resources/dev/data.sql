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

INSERT INTO service (id, business_id, active)
VALUES (1, 1, true),
       (2, 2, true),
       (3, 2, false);

INSERT INTO staff (id, business_id, sub, active)
VALUES (1, 1, "iamStaff111" true),
       (2, 2, "iamStaff222" true),
       (3, 2, "iamStaff333" true),
       (4, 2, "iamStaff444" true);

INSERT INTO schedule (start, end, staff_id, service_id, created_at, active)
VALUES ('2025-03-02', '2025-03-05', 1, 1, '2024-03-02T08:00:00', true),
       ('2025-03-06', '2025-03-15', 1, 1, '2024-03-02T08:00:00', true),
       ('2025-03-03', '2025-03-06', 2, 2, '2024-03-02T08:00:00', true),
       ('2025-03-07', '2025-03-20', 2, 2, '2024-03-02T08:00:00', true);

INSERT INTO schedule_pattern (schedule_id, repeat_days, pause_days)
VALUES (1, 3, 2),
       (1, 3, 5),
       (2, 2, 2),
       (2, 2, 5),
       (3, 2, 2),
       (3, 2, 4),
       (4, 3, 3),
       (4, 4, 3);

INSERT INTO schedule_exception_day (schedule_id, date)
VALUES (1, '2025-03-09'),
       (2, '2025-03-13');

INSERT INTO pattern_slots (schedule_pattern_id, start, end, cost, max_reservations)
VALUES (1, '08:00:00', '12:00:00', 20.0, 10),
       (1, '13:00:00', '17:00:00', 20.0, 10),
       (2, '09:00:00', '13:00:00', 25.0, 8),
       (2, '14:00:00', '18:00:00', 25.0, 8),
       (3, '10:00:00', '14:00:00', 18.0, 12),
       (3, '15:00:00', '19:00:00', 18.0, 12),
       (4, '11:00:00', '15:00:00', 22.0, 9),
       (4, '16:00:00', '20:00:00', 22.0, 9);

INSERT INTO exception_days_slots (schedule_exception_day_id, start, end, cost, max_reservations)
VALUES (1, '06:00:00', '14:00:00', 10.0, 10),
       (1, '15:00:00', '16:00:00', 20.0, 4),
       (2, '09:00:00', '18:00:00', 50, 2);
