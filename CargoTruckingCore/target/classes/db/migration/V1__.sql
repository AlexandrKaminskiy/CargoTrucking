ALTER TABLE storage
    ADD user_id BIGINT;

ALTER TABLE storage
    ALTER COLUMN user_id SET NOT NULL;

ALTER TABLE storage
DROP
CONSTRAINT fkbqlo403cq3yb63bv05nccw4j4;

ALTER TABLE storage
DROP
COLUMN user_id_id;