insert ignore into users (username, password, email, enabled)
values ('admin', '$2a$10$mXbNL1Ty5ZE/VQl/TvlLF.8pLSvF90UQlBtAGdDxenNcW1vrmIXDa', 'admin@admin', true),
       ('user', '$2a$10$P3ORe7rj0VD6qSlKlhzoo.XaivcL8hfL0QFYTQEFijeeoP2HZkjEq', 'user@user', true),
       ('artur', '$2a$10$uKFPUT2VFbVdRS7njcGqwOKi/uMIoR/GQdT8EBucka/Ka88KB08Tu', 'artur@artur', true);

insert ignore into authorities (username, authority)
values ('admin', 'ADMIN'),
       ('user', 'USER'),
       ('artur', 'USER');

insert ignore into authors (ID, firstname, lastname)
values (1, 'Henryk', 'Sienkiewicz'),
       (2, 'Adam', 'Mickiewicz'),
       (3, 'Juliusz', 'Słowacki'),
       (4, 'Stanisław', 'Wyspiański'),
       (5, 'Jan', 'Kochanowski'),
       (6, 'Czesław', 'Miłosz'),
       (7, 'Stefan', 'Żeromski'),
       (8, 'Bolesław', 'Prus'),
       (9, 'Jan', 'Brzechwa');

insert ignore into books(id, number_of_copies, title, author)
values (1, 7, 'Pan Tadeusz', 2),
       (2, 4, 'Quo Vadis', 1),
       (3, 3, 'Potop', 1),
       (4, 5, 'Balladyna', 3),
       (5, 4, 'Lalka', 8),
       (6, 3, 'Wesele', 4),
       (7, 6, 'Treny', 5),
       (8, 6, 'Zniewolony umysł', 6),
       (9, 5, 'Przedwiośnie', 7),
       (10, 4, 'Lokomotywa', 9);


insert ignore into reservations(id, reservation_date, reserved_book, username)
values (1, '2019-03-28 14:21:48.161000000', 1, 'user'),
       (2, '2019-03-27 08:01:43.161000000', 2, 'user'),
       (3, '2019-03-27 16:21:41.161000000', 3, 'artur'),
       (4, '2019-03-25 15:01:48.161000000', 3, 'user'),
       (5, '2019-03-26 11:03:10.161000000', 2, 'artur'),
       (6, '2019-03-28 12:18:48.161000000', 1, 'artur');
