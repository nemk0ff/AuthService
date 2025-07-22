-- 001-initial-schema.sql
CREATE TABLE roles
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(50) NOT NULL UNIQUE,
    description TEXT
);

INSERT INTO roles (name, description)
VALUES ('admin', 'Администратор системы с полными правами'),
       ('premium_user', 'Привилегированный пользователь с расширенными возможностями'),
       ('guest', 'Обычный пользователь с базовыми правами');

CREATE TABLE users
(
    id            SERIAL PRIMARY KEY,
    password      VARCHAR(255) NOT NULL,
    username      VARCHAR(100) UNIQUE,
    email         VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- DROP TABLE users CASCADE;
-- DROP TABLE roles CASCADE;
-- DROP TABLE user_roles CASCADE;