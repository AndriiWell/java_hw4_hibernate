--liquibase formatted sql
--changeset Andri:2-table-user-create
CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

--rollback DROP TABLE users;
