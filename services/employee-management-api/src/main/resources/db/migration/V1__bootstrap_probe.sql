CREATE TABLE bootstrap_probe (
    id NUMBER(19) PRIMARY KEY,
    description VARCHAR2(100 CHAR) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

INSERT INTO bootstrap_probe (id, description)
VALUES (1, 'flyway bootstrap validation');
