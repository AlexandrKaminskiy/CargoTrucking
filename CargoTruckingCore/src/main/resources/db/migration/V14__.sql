CREATE TABLE repairing_message
(
    id              BIGINT NOT NULL,
    uuid            VARCHAR(255),
    expiration_time BIGINT,
    email           VARCHAR(255),
    CONSTRAINT pk_repairing_message PRIMARY KEY (id)
);