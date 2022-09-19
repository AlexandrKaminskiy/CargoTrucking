CREATE TABLE carriage_status
(
    waybill_id        BIGINT NOT NULL,
    carriage_statuses VARCHAR(255)
);

CREATE TABLE cars
(
    id                 BIGINT NOT NULL,
    brand              VARCHAR(255),
    car_number         VARCHAR(255),
    price_for_km       INTEGER,
    consumption_for_km INTEGER,
    CONSTRAINT pk_cars PRIMARY KEY (id)
);

CREATE TABLE checkpoint_statuses
(
    checkpoint_id BIGINT NOT NULL,
    status        VARCHAR(255)
);

CREATE TABLE checkpoints
(
    id                    BIGINT NOT NULL,
    way_bill_id           BIGINT,
    address               VARCHAR(255),
    required_arrival_date TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_checkpoints PRIMARY KEY (id)
);

CREATE TABLE client
(
    id            BIGINT NOT NULL,
    name          VARCHAR(255),
    is_active     BOOLEAN,
    active_date   TIMESTAMP WITHOUT TIME ZONE,
    admin_info_id BIGINT,
    CONSTRAINT pk_client PRIMARY KEY (id)
);

CREATE TABLE invalid_jwt
(
    id  BIGINT NOT NULL,
    jwt VARCHAR(255),
    CONSTRAINT pk_invalid_jwt PRIMARY KEY (id)
);

CREATE TABLE invoice_status
(
    invoice_number VARCHAR(255) NOT NULL,
    status         VARCHAR(255)
);

CREATE TABLE invoices
(
    id               VARCHAR(255) NOT NULL,
    creation_date    TIMESTAMP WITHOUT TIME ZONE,
    verified_date    TIMESTAMP WITHOUT TIME ZONE,
    storage_id       BIGINT,
    creator_id       BIGINT,
    product_owner_id BIGINT,
    driver_id        BIGINT,
    CONSTRAINT pk_invoices PRIMARY KEY (id)
);

CREATE TABLE payment
(
    id      BIGINT NOT NULL,
    payment DOUBLE PRECISION,
    date    TIMESTAMP WITHOUT TIME ZONE,
    user_id BIGINT,
    CONSTRAINT pk_payment PRIMARY KEY (id)
);

CREATE TABLE product_owner
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_product_owner PRIMARY KEY (id)
);

CREATE TABLE product_status
(
    product_id     BIGINT NOT NULL,
    product_status VARCHAR(255)
);

CREATE TABLE products
(
    id               BIGINT NOT NULL,
    name             VARCHAR(255),
    amount           INTEGER,
    creator_id       BIGINT,
    product_owner_id BIGINT,
    invoice_id       VARCHAR(255),
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE repairing_message
(
    id              BIGINT NOT NULL,
    uuid            VARCHAR(255),
    expiration_time BIGINT,
    email           VARCHAR(255),
    old_email       VARCHAR(255),
    CONSTRAINT pk_repairing_message PRIMARY KEY (id)
);

CREATE TABLE role
(
    user_id BIGINT NOT NULL,
    roles   VARCHAR(255)
);

CREATE TABLE status
(
    client_id BIGINT NOT NULL,
    status    VARCHAR(255)
);

CREATE TABLE storage
(
    id         BIGINT NOT NULL,
    name       VARCHAR(255),
    address    VARCHAR(255),
    client_id  BIGINT,
    clients_id BIGINT,
    CONSTRAINT pk_storage PRIMARY KEY (id)
);

CREATE TABLE users
(
    id           BIGINT NOT NULL,
    name         VARCHAR(255),
    surname      VARCHAR(255),
    patronymic   VARCHAR(255),
    born_date    TIMESTAMP WITHOUT TIME ZONE,
    email        VARCHAR(255),
    town         VARCHAR(255),
    street       VARCHAR(255),
    house        INTEGER,
    flat         INTEGER,
    login        VARCHAR(255),
    password     VARCHAR(255),
    passport_num VARCHAR(255),
    issued_by    VARCHAR(255),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE way_bill
(
    id          BIGINT NOT NULL,
    invoice_id  VARCHAR(255),
    distance    INTEGER,
    car_id      BIGINT,
    end_date    TIMESTAMP WITHOUT TIME ZONE,
    verifier_id BIGINT,
    consumption INTEGER,
    income      INTEGER,
    profit      INTEGER,
    CONSTRAINT pk_way_bill PRIMARY KEY (id)
);

ALTER TABLE checkpoints
    ADD CONSTRAINT FK_CHECKPOINTS_ON_WAY_BILL FOREIGN KEY (way_bill_id) REFERENCES way_bill (id);

ALTER TABLE client
    ADD CONSTRAINT FK_CLIENT_ON_ADMININFO FOREIGN KEY (admin_info_id) REFERENCES users (id);

ALTER TABLE invoices
    ADD CONSTRAINT FK_INVOICES_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id);

ALTER TABLE invoices
    ADD CONSTRAINT FK_INVOICES_ON_DRIVER FOREIGN KEY (driver_id) REFERENCES users (id);

ALTER TABLE invoices
    ADD CONSTRAINT FK_INVOICES_ON_PRODUCTOWNER FOREIGN KEY (product_owner_id) REFERENCES product_owner (id);

ALTER TABLE invoices
    ADD CONSTRAINT FK_INVOICES_ON_STORAGE FOREIGN KEY (storage_id) REFERENCES storage (id);

ALTER TABLE payment
    ADD CONSTRAINT FK_PAYMENT_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CREATOR FOREIGN KEY (creator_id) REFERENCES users (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_INVOICE FOREIGN KEY (invoice_id) REFERENCES invoices (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_PRODUCTOWNER FOREIGN KEY (product_owner_id) REFERENCES product_owner (id);

ALTER TABLE storage
    ADD CONSTRAINT FK_STORAGE_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE storage
    ADD CONSTRAINT FK_STORAGE_ON_CLIENTS FOREIGN KEY (clients_id) REFERENCES client (id);

ALTER TABLE way_bill
    ADD CONSTRAINT FK_WAY_BILL_ON_CAR FOREIGN KEY (car_id) REFERENCES cars (id);

ALTER TABLE way_bill
    ADD CONSTRAINT FK_WAY_BILL_ON_INVOICE FOREIGN KEY (invoice_id) REFERENCES invoices (id);

ALTER TABLE way_bill
    ADD CONSTRAINT FK_WAY_BILL_ON_VERIFIER FOREIGN KEY (verifier_id) REFERENCES users (id);

ALTER TABLE carriage_status
    ADD CONSTRAINT fk_carriage_status_on_way_bill FOREIGN KEY (waybill_id) REFERENCES way_bill (id);

ALTER TABLE checkpoint_statuses
    ADD CONSTRAINT fk_checkpoint_statuses_on_checkpoint FOREIGN KEY (checkpoint_id) REFERENCES checkpoints (id);

ALTER TABLE invoice_status
    ADD CONSTRAINT fk_invoice_status_on_invoice FOREIGN KEY (invoice_number) REFERENCES invoices (id);

ALTER TABLE product_status
    ADD CONSTRAINT fk_product_status_on_product FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE role
    ADD CONSTRAINT fk_role_on_user FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE status
    ADD CONSTRAINT fk_status_on_client FOREIGN KEY (client_id) REFERENCES client (id);