CREATE TABLE "user"
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
    CONSTRAINT pk_user PRIMARY KEY (id)
);

ALTER TABLE storage
    ADD user_id BIGINT;

ALTER TABLE storage
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE user_roles
    ADD CONSTRAINT fk_userol_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);

ALTER TABLE storage
DROP
COLUMN user_id_id;