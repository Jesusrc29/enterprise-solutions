INSERT INTO roles (name, description)
VALUES ('ADMIN', 'System administrator');

INSERT INTO roles (name, description)
VALUES ('HR_MANAGER', 'Human resources manager');

INSERT INTO roles (name, description)
VALUES ('EMPLOYEE', 'Employee role');

INSERT INTO users (username, password_hash, enabled)
VALUES ('admin', '$2a$10$qi76k06bX0K7.jaDZNPueOTA0bVLUr1hqZ3cP0PBmyNvum5j56RVi', 1);

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u
JOIN roles r ON r.name = 'ADMIN'
WHERE u.username = 'admin';
