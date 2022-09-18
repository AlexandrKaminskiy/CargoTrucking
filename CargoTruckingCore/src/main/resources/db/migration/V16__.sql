ALTER TABLE users
DROP
CONSTRAINT fk_users_on_client;

ALTER TABLE users
DROP
COLUMN client_id;