CREATE TABLE client
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
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE storage
(
    id        BIGINT NOT NULL,
    name      VARCHAR(20),
    address   VARCHAR(20),
    client_id BIGINT NOT NULL,
    CONSTRAINT pk_storage PRIMARY KEY (id)
);

CREATE TABLE user_role
(
    client_id BIGINT NOT NULL,
    user_role VARCHAR(255)
);

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_client FOREIGN KEY (client_id) REFERENCES client (id);