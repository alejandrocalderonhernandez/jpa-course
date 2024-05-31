CREATE TABLE departments (
                             id INTEGER PRIMARY KEY AUTO_INCREMENT,
                             name VARCHAR(100) NOT NULL
);


CREATE TABLE employees (
                           id INTEGER PRIMARY KEY AUTO_INCREMENT,
                           name VARCHAR(100) NOT NULL,
                           email VARCHAR(100) UNIQUE NOT NULL,
                           department_id INTEGER NOT NULL,
                           FOREIGN KEY (department_id) REFERENCES departments(id)
);

INSERT INTO departments (name) VALUES ('Lightweight');
INSERT INTO departments (name) VALUES ('Welterweight');
INSERT INTO departments (name) VALUES ('Heavyweight');

INSERT INTO employees (name, email, department_id) VALUES ('Khabib Nurmagomedov', 'khabib@ufc.com', 1);
INSERT INTO employees (name, email, department_id) VALUES ('Conor McGregor', 'conor@ufc.com', 1);
INSERT INTO employees (name, email, department_id) VALUES ('Kamaru Usman', 'kamaru@ufc.com', 2);
INSERT INTO employees (name, email, department_id) VALUES ('Jorge Masvidal', 'jorge@ufc.com', 2);
INSERT INTO employees (name, email, department_id) VALUES ('Francis Ngannou', 'francis@ufc.com', 3);
INSERT INTO employees (name, email, department_id) VALUES ('Stipe Miocic', 'stipe@ufc.com', 3);