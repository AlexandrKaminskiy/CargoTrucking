CREATE TABLE checkpoint_statuses
(
    checkpoint_id BIGINT NOT NULL,
    status        VARCHAR(255)
);

ALTER TABLE checkpoint_statuses
    ADD CONSTRAINT fk_checkpoint_statuses_on_checkpoint FOREIGN KEY (checkpoint_id) REFERENCES checkpoints (id);