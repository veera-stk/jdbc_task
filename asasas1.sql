CREATE DATABASE training_institute;
USE training_institute;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    email VARCHAR(50),
    phone VARCHAR(15)
);

CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_name VARCHAR(50),
    duration VARCHAR(20)
);

CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    FOREIGN KEY(student_id) REFERENCES students(student_id),
    FOREIGN KEY(course_id) REFERENCES courses(course_id)
);

CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    attendance_date DATE,
    status VARCHAR(10),
    FOREIGN KEY(student_id) REFERENCES students(student_id),
    FOREIGN KEY(course_id) REFERENCES courses(course_id)
);

CREATE TABLE marks (
    mark_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    marks INT,
    FOREIGN KEY(student_id) REFERENCES students(student_id),
    FOREIGN KEY(course_id) REFERENCES courses(course_id)
);


select * from students;
select * from courses;
select * from enrollments;
select * from attendance;
select * from marks;

CREATE TABLE employee_leave (
    leave_id INT PRIMARY KEY AUTO_INCREMENT,
    emp_id INT,
    emp_name VARCHAR(50),
    leave_type VARCHAR(30),
    from_date DATE,
    to_date DATE,
    reason VARCHAR(100),
    status VARCHAR(20)
);

select * from employee_leave;

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

