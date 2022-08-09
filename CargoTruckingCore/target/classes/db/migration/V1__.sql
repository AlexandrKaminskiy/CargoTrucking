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
    user_roles   VARCHAR(255),
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