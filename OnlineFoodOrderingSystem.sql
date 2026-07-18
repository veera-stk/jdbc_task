CREATE DATABASE online_food_ordering;

use online_food_ordering;


CREATE TABLE customers(
customer_id INT PRIMARY KEY AUTO_INCREMENT,
customer_name VARCHAR(50),
phone VARCHAR(15)
);

CREATE TABLE menu(
item_id INT PRIMARY KEY AUTO_INCREMENT,
item_name VARCHAR(50),
price DOUBLE
);

INSERT INTO menu(item_name,price) VALUES
('Pizza',250),
('Burger',120),
('Fried Rice',180),
('Sandwich',90),
('Coffee',70);

CREATE TABLE orders(
order_id INT PRIMARY KEY AUTO_INCREMENT,
customer_id INT,
order_date DATE,
total DOUBLE,
FOREIGN KEY(customer_id)
REFERENCES customers(customer_id)
);

CREATE TABLE order_items(
order_item_id INT PRIMARY KEY AUTO_INCREMENT,
order_id INT,
item_id INT,
quantity INT,
amount DOUBLE,
FOREIGN KEY(order_id)
REFERENCES orders(order_id),
FOREIGN KEY(item_id)
REFERENCES menu(item_id)
);

