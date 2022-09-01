ALTER TABLE way_bill
DROP
CONSTRAINT fkefs5ht87c3viw6flflm92q3np;

ALTER TABLE users
DROP
COLUMN date;

ALTER TABLE way_bill
DROP
COLUMN user_id;

ALTER TABLE storage
ALTER
COLUMN address TYPE VARCHAR(255) USING (address::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN email TYPE VARCHAR(255) USING (email::VARCHAR(255));

ALTER TABLE invoices
ALTER
COLUMN id TYPE VARCHAR(255) USING (id::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN issued_by TYPE VARCHAR(255) USING (issued_by::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN login TYPE VARCHAR(255) USING (login::VARCHAR(255));

ALTER TABLE client
ALTER
COLUMN name TYPE VARCHAR(255) USING (name::VARCHAR(255));

ALTER TABLE product_owner
ALTER
COLUMN name TYPE VARCHAR(255) USING (name::VARCHAR(255));

ALTER TABLE products
ALTER
COLUMN name TYPE VARCHAR(255) USING (name::VARCHAR(255));

ALTER TABLE storage
ALTER
COLUMN name TYPE VARCHAR(255) USING (name::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN name TYPE VARCHAR(255) USING (name::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN passport_num TYPE VARCHAR(255) USING (passport_num::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN password TYPE VARCHAR(255) USING (password::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN patronymic TYPE VARCHAR(255) USING (patronymic::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN street TYPE VARCHAR(255) USING (street::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN surname TYPE VARCHAR(255) USING (surname::VARCHAR(255));

ALTER TABLE users
ALTER
COLUMN town TYPE VARCHAR(255) USING (town::VARCHAR(255));