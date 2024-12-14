--liquibase formatted sql
--changeset Andri:5-add-column-email

ALTER TABLE users ADD COLUMN email VARCHAR(255);

--rollback ALTER TABLE users DROP COLUMN email;
