drop database parking;
CREATE DATABASE parking;
USE parking;

CREATE TABLE Role(
	id CHAR(10) PRIMARY KEY NOT NULL,
    name CHAR(20)
);
CREATE TABLE Employee(
	id CHAR(10) PRIMARY KEY NOT NULL,
    name CHAR(20),
    password CHAR(20),
    id_role CHAR(10),
    FOREIGN KEY (id_role) REFERENCES Role (id)
);
CREATE TABLE Revenue(
	id int PRIMARY KEY NOT NULL,
    time Date,
    total int
);
CREATE TABLE Positions(
	id CHAR(10) PRIMARY KEY NOT NULL,
    status BOOLEAN
);
CREATE TABLE Ticket(
	id CHAR(10) PRIMARY KEY NOT NULL,
    time DATETIME,
    ticket_type BOOLEAN,
    price INT,
    status BOOLEAN,
    id_employee CHAR(10),
    number_car INT,
	id_revenue int,
    id_position CHAR(10),
    FOREIGN KEY (id_employee) REFERENCES Employee (id),
    FOREIGN KEY (id_revenue) REFERENCES Revenue (id),
    FOREIGN KEY (id_position) REFERENCES Positions (id)
);

INSERT INTO Role(id,name) VALUES ("R01","Admin");
INSERT INTO Role(id,name) VALUES ("R02","Cashier");

INSERT INTO Employee(id,name,password,id_role) VALUES ("NV01","Alice","123","R01");
INSERT INTO Employee(id,name,password,id_role) VALUES ("NV01","Alice","123","R02");
INSERT INTO Employee(id,name,password,id_role) VALUES ("NV02","Tex","123","R02");

INSERT INTO Positions(id,status) VALUES ("P01",0);
INSERT INTO Positions(id,status) VALUES ("P03",0);
INSERT INTO Positions(id,status) VALUES ("P02",0);
INSERT INTO Positions(id,status) VALUES ("P04",0);

INSERT INTO Revenue(id,time,total) VALUES (0,"2023-06-03",5000);

INSERT INTO Ticket(id,time,ticket_type,price,status,id_employee,number_car,id_revenue,id_position) VALUES ("T001","2023-06-03 20:59:59",0,5000,0,"NV01",123456,0,"P01");