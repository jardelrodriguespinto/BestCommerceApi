CREATE TABLE addresses (
    id SERIAL PRIMARY KEY,
    number INT NOT NULL,
    neighbourhood VARCHAR(255),
    state VARCHAR(255),
    city VARCHAR(255),
    country VARCHAR(255)
);