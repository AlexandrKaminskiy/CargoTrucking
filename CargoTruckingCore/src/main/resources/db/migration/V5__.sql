ALTER TABLE users_products
DROP
CONSTRAINT fk5aqj5juhtwinl80hqwe9dimtc;

ALTER TABLE users_products
DROP
CONSTRAINT fk7j1q4wj87yy0fmovuwq4yf7it;

ALTER TABLE products
DROP
CONSTRAINT fkco4dce57e0eusmy0ok6dk16xo;

DROP TABLE users_products CASCADE;

ALTER TABLE products
DROP
COLUMN product_id;