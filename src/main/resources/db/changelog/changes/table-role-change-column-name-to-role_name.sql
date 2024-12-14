--liquibase formatted sql
--changeset Andri:6-table-role-change-name-to-role_name
alter table roles rename column name to role_name;
--rollback alter table roles rename column role_name to name;