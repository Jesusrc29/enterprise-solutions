ALTER TABLE users ADD (
    email VARCHAR2(150 CHAR)
);

UPDATE users
SET email = 'admin@enterprise.local'
WHERE username = 'admin'
  AND email IS NULL;

ALTER TABLE users MODIFY (
    email VARCHAR2(150 CHAR) NOT NULL
);

ALTER TABLE users ADD (
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

ALTER TABLE users ADD CONSTRAINT uk_users_email UNIQUE (email);
