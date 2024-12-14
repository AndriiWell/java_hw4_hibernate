--liquibase formatted sql
--changeset Andri:4-table-user-insert-few-recs
insert into `users` (id, name) VALUES (1, 'Ivam');
insert into `users` (id, name) VALUES (2, 'Lariza');

insert into `roles` (id, name) VALUES (1, 'Admin');
insert into `roles` (id, name) VALUES (2, 'Staff');

insert into `user_role` (user_id, role_id) VALUES (1, 1);
insert into `user_role` (user_id, role_id) VALUES (2, 2);

--rollback DELETE FROM users WHERE id = 1;
--rollback DELETE FROM users WHERE id = 2;
--rollback DELETE FROM roles WHERE id IN (1, 2);
--rollback DELETE FROM user_role WHERE user_id IN (1, 2);

