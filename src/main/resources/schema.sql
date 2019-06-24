DROP TABLE IF EXISTS user;
CREATE TABLE users(
    id serial PRIMARY KEY,
    userADid VARCHAR(100),
    firstName VARCHAR(100),
    lastName VARCHAR(100));