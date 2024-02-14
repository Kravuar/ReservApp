INSERT INTO account (username, email, 'email-verified', 'password-encrypted')
VALUES ('test1', 'beb1@a', false, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test2', 'beb2@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test3', 'beb3@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test4', 'beb4@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test5', 'beb5@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test6', 'beb6@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test7', 'beb7@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test8', 'beb8@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa'),
       ('test9', 'beb9@a', true, '{bcrypt}$2a$10$J2C5HX2/QTY4qVE0tKaStOSLJ8Tyet6v.MSJ5xhbgPDjJwIReJWXa');

INSERT INTO business (name, ownerId)
VALUES ('ABC Company', 1),
       ('XYZ Corporation', 2),
       ('123 Enterprises', 3),
       ('Alpha Industries', 4),
       ('Beta Corporation', 5),
       ('Gamma Co.', 6),
       ('Delta Enterprises', 7),
       ('Omega Ltd.', 8),
       ('Sigma Corp', 9),
       ('Zeta Corporation', 1),
       ('Epsilon Enterprises', 2),
       ('Theta Co.', 3),
       ('Nu Industries', 4),
       ('Iota Corporation', 4),
       ('Lambda Ltd.', 2);
