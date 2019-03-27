insert ignore into users (username, password, email, enabled)
values ('admin', '$2a$10$mXbNL1Ty5ZE/VQl/TvlLF.8pLSvF90UQlBtAGdDxenNcW1vrmIXDa', 'admin@admin', true),
       ('user', '$2a$10$P3ORe7rj0VD6qSlKlhzoo.XaivcL8hfL0QFYTQEFijeeoP2HZkjEq', 'user@user', true);

insert ignore into authorities (username, authority)
values ('admin', 'ADMIN'),
       ('user', 'USER');

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

insert ignore into books(id, number_of_borrowed_copies, number_of_copies, title, author)
values (1001, 0, 7, 'Pan Tadeusz', 2),
       (1002, 0, 4, 'Quo Vadis', 1),
       (1003, 0, 3, 'Potop', 1),
       (1004, 0, 5, 'Balladyna', 3),
       (1005, 0, 4, 'Lalka', 8),
       (1006, 0, 3, 'Wesele', 4),
       (1007, 0, 6, 'Treny', 5),
       (1008, 0, 6, 'Zniewolony umysł', 6),
       (1009, 0, 5, 'Przedwiośnie', 7),
       (1010, 0, 4, 'Lokomotywa', 9);

alter sequence hibernate_sequence restart with 10000;