create table sessions
(
    id         varchar(36)  not null
        primary key,
    activetime timestamp(6) not null,
    user_id    bigint       not null

);
ALTER TABLE IF EXISTS sessions
    ADD CONSTRAINT FKruie73rneumyyd1bgo6qw8vjt
    FOREIGN KEY (user_id)
    REFERENCES users(id);