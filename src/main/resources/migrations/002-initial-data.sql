-- Вставка 10 тестовых пользователей с хешированными паролями (используется bcrypt)
-- Пароли для всех пользователей: ""

INSERT INTO users (username, password, email)
VALUES ('admin_user', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'admin@example.com'),
       ('premium_user1', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'premium1@example.com'),
       ('premium_user2', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'premium2@example.com'),
       ('guest_user1', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'guest1@example.com'),
       ('guest_user2', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'guest2@example.com'),
       ('guest_user3', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'guest3@example.com'),
       ('mixed_user1', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'mixed1@example.com'),
       ('mixed_user2', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'mixed2@example.com'),
       ('test_user1', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'test1@example.com'),
       ('test_user2', '$2a$10$Smy9JptsypUyVBHOTMidTeh3pEZ9UA1uoGv6H5yLhqXXdLMkQsTKK', 'test2@example.com');

-- Назначение ролей пользователям
INSERT INTO user_roles (user_id, role_id)
VALUES
-- admin_user - администратор
(1, 1),
-- premium_user1 и premium_user2 - премиум пользователи
(2, 2),
(3, 2),
-- guest_user1, guest_user2, guest_user3 - обычные пользователи
(4, 3),
(5, 3),
(6, 3),
-- mixed_user1 - премиум + обычный
(7, 2),
(7, 3),
-- mixed_user2 - администратор + премиум
(8, 1),
(8, 2),
-- test_user1 и test_user2 - обычные пользователи
(9, 3),
(10, 3);