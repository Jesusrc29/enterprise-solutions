ALTER TABLE users ADD (
    email VARCHAR2(150 CHAR)
);

UPDATE users
SET email = CASE
    WHEN username = 'admin' THEN 'admin@enterprise.local'
    ELSE LOWER(username) || '.' || TO_CHAR(id) || '@local.enterprise'
END
WHERE email IS NULL;

UPDATE users
SET email = LOWER(email)
WHERE email IS NOT NULL;

ALTER TABLE users ADD (
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

UPDATE users
SET updated_at = created_at
WHERE updated_at IS NULL;

ALTER TABLE users MODIFY (
    email VARCHAR2(150 CHAR) NOT NULL
);

ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE (email);
