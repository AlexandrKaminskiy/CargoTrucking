CREATE TABLE role
(
    client_id BIGINT NOT NULL,
    roles     VARCHAR(255)
);

CREATE TABLE storage
(
    id      BIGINT NOT NULL,
    name    VARCHAR(20),
    address VARCHAR(20),
    user_id BIGINT NOT NULL,
    CONSTRAINT pk_storage PRIMARY KEY (id)
);

CREATE TABLE users
(
    id           BIGINT NOT NULL,
    name         VARCHAR(20),
    surname      VARCHAR(20),
    patronymic   VARCHAR(20),
    date         TIMESTAMP WITHOUT TIME ZONE,
    email        VARCHAR(50),
    town         VARCHAR(20),
    street       VARCHAR(20),
    house        INTEGER,
    flat         INTEGER,
    login        VARCHAR(20),
    password     VARCHAR(72),
    passport_num VARCHAR(30),
    issued_by    VARCHAR(50),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE role
    ADD CONSTRAINT fk_role_on_user FOREIGN KEY (client_id) REFERENCES users (id);