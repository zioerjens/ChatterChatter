--INSERT INTO USER (id, username, firstname, lastname, email, last_login_date, join_date, password, is_locked, is_active) VALUES (1, 'admin', 'admin', 'admin', 'admin@admin.ch', now(), now(), 'admin', false, true);

--INSERT INTO AUTHORITY (id, authority) VALUES (1, 'USER'), (2, 'ADMIN');

--INSERT INTO USER_AUTHORITIES (user_id, authorities_id) VALUES(1,1), (1,2);