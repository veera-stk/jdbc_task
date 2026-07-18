CREATE DATABASE jdbc_task;
USE jdbc_task;

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

