ALTER TABLE storage
    ADD client_id BIGINT;

ALTER TABLE storage
    ALTER COLUMN client_id SET NOT NULL;

ALTER TABLE user_role
    ADD user_id BIGINT;

ALTER TABLE user_role
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE user_role
    ADD CONSTRAINT fk_user_role_on_client FOREIGN KEY (user_id) REFERENCES client (id);

ALTER TABLE user_role
DROP
CONSTRAINT fkg1nbal3g4qaxtvdm1ogyxki8f;

ALTER TABLE storage
DROP
CONSTRAINT fkplggb0e25p541sie2qv0boa50;

ALTER TABLE user_role
DROP
COLUMN client_id;

ALTER TABLE storage
DROP
COLUMN client_id_id;