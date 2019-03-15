merge into users (username, password, email, enabled) values ('admin', '$2a$10$mXbNL1Ty5ZE/VQl/TvlLF.8pLSvF90UQlBtAGdDxenNcW1vrmIXDa', 'admin@admin', true);
merge into authorities (username, authority) values ('admin', 'ADMIN');
merge into users (username, password, email, enabled) values ('user', '$2a$10$P3ORe7rj0VD6qSlKlhzoo.XaivcL8hfL0QFYTQEFijeeoP2HZkjEq', 'user@user', true);
merge into authorities (username, authority) values ('user', 'USER');

