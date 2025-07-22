-- test-initial-data.sql
-- Пароль для всех пользователей: "TestPassword123!"
INSERT INTO users (password, username, email)
VALUES
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'admin_user', 'admin@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'premium_user1', 'premium1@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'premium_user2', 'premium2@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'guest_user1', 'guest1@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'guest_user2', 'guest2@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'guest_user3', 'guest3@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'mixed_user1', 'mixed1@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'mixed_user2', 'mixed2@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'test_user1', 'test1@example.com'),
    ('$2a$10$nGSytfuznHGN4lLgtYxI8.fR6wECXaYX8WaQwXZYOVV/F75s3dUji', 'test_user2', 'test2@example.com');

-- Назначение ролей пользователям (используем текущие названия ролей без ROLE_)
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