INSERT INTO role (name)
VALUES ('USER'),
       ('CHIPPER'),
       ('ADMIN');

INSERT INTO account (first_name, last_name, email, password, role_id)
VALUES ('adminFirstName', 'adminLastName', 'admin@simbirsoft.com', '3fc0a7acf087f549ac2b266baf94b8b1', 1),
       ('chipperFirstName', 'chipperLastName', 'chipper@simbirsoft.com', '3fc0a7acf087f549ac2b266baf94b8b1', 2),
       ('userFirstName', 'userLastName', 'user@simbirsoft.com', '3fc0a7acf087f549ac2b266baf94b8b1', 3);
