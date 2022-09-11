CREATE TABLE invoice_status
(
    invoice_number VARCHAR(255) NOT NULL,
    invoice_status VARCHAR(255)
);

ALTER TABLE invoices
    ADD creation_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE invoices
    ADD verified_date TIMESTAMP WITHOUT TIME ZONE;

ALTER TABLE invoice_status
    ADD CONSTRAINT fk_invoice_status_on_invoice FOREIGN KEY (invoice_number) REFERENCES invoices (id);