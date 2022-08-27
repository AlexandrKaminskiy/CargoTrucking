CREATE TABLE client
(
    id   BIGINT NOT NULL,
    name VARCHAR(30),
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE status
(
    client_id BIGINT NOT NULL
);

ALTER TABLE role
    ADD user_id BIGINT;

ALTER TABLE role
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE storage
    ADD user_id BIGINT;

ALTER TABLE storage
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE role
    ADD CONSTRAINT fk_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE status
    ADD CONSTRAINT fk_status_on_client FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE role
DROP
CONSTRAINT fkkgbapgxmf83iba95ytrrdg4yv;

ALTER TABLE storage
DROP
CONSTRAINT fkpm372dgqt222ueu5qwvm1lowd;

ALTER TABLE role
DROP
COLUMN client_id;

ALTER TABLE storage
DROP
COLUMN user_id_id;