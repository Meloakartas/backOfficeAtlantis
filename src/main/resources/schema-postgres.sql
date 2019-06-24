DROP TABLE IF EXISTS user;
CREATE TABLE user(id serial PRIMARY KEY, userADid VARCHAR(100), firstName VARCHAR(100), lastName VARCHAR(100));

CREATE TABLE user_device (
  bill_id    int REFERENCES bill (bill_id) ON UPDATE CASCADE ON DELETE CASCADE
, product_id int REFERENCES product (product_id) ON UPDATE CASCADE
, amount     numeric NOT NULL DEFAULT 1
, CONSTRAINT bill_product_pkey PRIMARY KEY (bill_id, product_id)  -- explicit pk
);

DROP TABLE IF EXISTS device;
CREATE TABLE device(
    id serial PRIMARY KEY,
    macAddress VARCHAR(100),
    name VARCHAR(100),
    lastName VARCHAR(100),
    FOREIGN KEY (deviceType_id) REFERENCES deviceType(id) ON DELETE CASCADE);

DROP TABLE IF EXISTS deviceType;
CREATE TABLE deviceType(
    id serial PRIMARY KEY,
    name VARCHAR(100));
