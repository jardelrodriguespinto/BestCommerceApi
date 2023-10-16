CREATE TABLE customers (
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    age INT,
    customer_type_id INT,
    address_id INT,
    FOREIGN KEY (customer_type_id) REFERENCES customer_type(id),
    FOREIGN KEY (address_id) REFERENCES address(id)
);
