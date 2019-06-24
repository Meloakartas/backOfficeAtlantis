DROP TABLE IF EXISTS user;
CREATE TABLE users(
    id serial PRIMARY KEY,
    userADid VARCHAR(100),
    firstName VARCHAR(100),
    lastName VARCHAR(100));

DROP TABLE IF EXISTS deviceType;
CREATE TABLE deviceType(
    id serial PRIMARY KEY,
    name VARCHAR(100));

DROP TABLE IF EXISTS device;
CREATE TABLE device(
    id serial PRIMARY KEY,
    macAddress VARCHAR(100),
    name VARCHAR(100),
    deviceType_id INTEGER REFERENCES deviceType(id) ON DELETE CASCADE);

CREATE TABLE user_device (
    user_id INTEGER REFERENCES users(id) ON UPDATE CASCADE ON DELETE CASCADE,
    device_id INTEGER REFERENCES device(id) ON UPDATE CASCADE,
    CONSTRAINT id PRIMARY KEY (user_id, device_id)
);