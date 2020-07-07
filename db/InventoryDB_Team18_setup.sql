/* Deliverable 3
*  Combination of Create and Insert files
*  Team 18
*  Jonah Cole, Ryan Cluff, Dylan Sund
*/

CREATE TABLE CUSTOMER(
Email VARCHAR(50) NOT NULL,
Name VARCHAR(15) NOT NULL,
Address VARCHAR(30),
PhoneNumber VARCHAR(11),
PRIMARY KEY(Email));

CREATE TABLE EMPLOYEE(
SSN CHAR(9) NOT NULL,
Name VARCHAR(15) NOT NULL,
Address VARCHAR(30),
PhoneNumber VARCHAR(11),
PRIMARY KEY(SSN));

CREATE TABLE CUSTOMER_ORDER(
OrderId VARCHAR(25) NOT NULL,
CEmail VARCHAR(50),
DatePlaced Date NOT NULL,
FOREIGN KEY(CEmail) REFERENCES CUSTOMER(Email),
PRIMARY KEY(OrderId));

CREATE TABLE PRODUCT(
ProductId VARCHAR(30) NOT NULL,
Name VARCHAR(15),
Price VARCHAR(10),
Description VARCHAR(60),
PRIMARY KEY(ProductId));

CREATE TABLE LOCATION(
LocationId VARCHAR(20) NOT NULL,
Address VARCHAR(30),
PhoneNumber VARCHAR(11),
PRIMARY KEY(LocationId));

CREATE TABLE ORDER_CONTAINS(
OrderId VARCHAR(25) NOT NULL,
ProductId VARCHAR(30) NOT NULL,
ItemCount INT,
FOREIGN KEY(OrderId) REFERENCES CUSTOMER_ORDER(OrderId),
FOREIGN KEY(ProductId) REFERENCES PRODUCT(ProductId),
PRIMARY KEY(OrderId, ProductId));

CREATE TABLE FULFILLED_BY(
OrderId VARCHAR(25) NOT NULL,
eSSN CHAR(9) NOT NULL,
FOREIGN KEY(OrderId) REFERENCES CUSTOMER_ORDER(OrderId),
FOREIGN KEY(eSSN) REFERENCES EMPLOYEE(SSN),
PRIMARY KEY(OrderId, eSSN));

CREATE TABLE WORKS_AT(
LocationId VARCHAR(20) NOT NULL,
eSSN CHAR(9) NOT NULL,
FOREIGN KEY(LocationId) REFERENCES LOCATION(LocationId),
FOREIGN KEY(eSSN) REFERENCES EMPLOYEE(SSN),
PRIMARY KEY(LocationId, eSSN));

CREATE TABLE STORED_AT(
LocationId VARCHAR(20) NOT NULL,
ProductId VARCHAR(30) NOT NULL,
ItemCount INT,
FOREIGN KEY(LocationId) REFERENCES LOCATION(LocationId),
FOREIGN KEY(ProductId) REFERENCES PRODUCT(ProductId),
PRIMARY KEY(LocationId, ProductId));

INSERT INTO `CUSTOMER` (`Email`, `Name`, `Address`, `PhoneNumber`)
VALUES ('johnsmith@yahoo.com', 'John Smith', '3245 1st St.', '555-2398'),
('laurenwalker@gmail.com', 'Lauren Walker', '7634 Main St.', '555-4602'),
('fionamills@yahoo.com', 'Fiona Mills', '8904 Vine St.', '555-3100'),
('brianterry@gmail.com', 'Brian Terry', '5672 4th St.', '555-8862');

INSERT INTO `EMPLOYEE` (`SSN`, `Name`, `Address`, `PhoneNumber`)
VALUES ('820850932', 'Irene McLean', '9879 8th St.', '555-0987'),
('098438725', 'Audrey Paige', '3249 Oak St.', '555-1423'),
('142853866', 'Colin Reese', '3199 Main St.', '555-0192'),
('947880785', 'Liam Ellison', '1245 1st St.', '555-1100');

INSERT INTO `CUSTOMER_ORDER` (`OrderId`, `CEmail`, `DatePlaced`)
VALUES ('111', 'johnsmith@yahoo.com', '2020-03-21'),
('222', 'laurenwalker@gmail.com', '2020-04-20'),
('333', 'fionamills@yahoo.com', '2020-04-17'),
('444', 'brianterry@gmail.com', '2020-05-11');

INSERT INTO `PRODUCT` (`ProductId`, `Name`, `Price`, `Description`)
VALUES ('4683', 'Hand Sanitizer', '3.00', 'Sanitizes hands.'),
('9087', 'Face Mask', '1.00', 'Keeps persons germs contained close to them.'),
('1234', 'Hand Soap', '2.00', 'Cleanses hands.'),
('7856', 'Gloves', '2.00', 'Cover hands to protet from germs.');

INSERT INTO `ORDER_CONTAINS` (`OrderId`, `ProductId`, `ItemCount`)
VALUES ('222', '9087', 5),
('111', '4683', 1),
('333', '1234', 2),
('444', '7856', 4);

INSERT INTO `FULFILLED_BY` (`OrderId`, `eSSN`)
VALUES ('111', '820850932'),
('222', '098438725'),
('333', '142853866'),
('444', '947880785');

INSERT INTO `LOCATION` (`LocationId`, `Address`, `PhoneNumber`)
VALUES ('Seattle', '0099 E 4th St.', '555-0456'),
('Anaheim', '2288 2nd St.', '555-1106'),
('Chicago', '3377 Angel St.', '555-2205'),
('Pheonix', '4466 20th St.', '555-3780');

INSERT INTO `WORKS_AT` (`LocationId`, `eSSN`)
VALUES ('Seattle', '947880785'),
('Anaheim', '142853866'),
('Pheonix', '098438725'),
('Chicago', '820850932');

INSERT INTO `STORED_AT` (`LocationId`, `ProductId`, `ItemCount`)
VALUES ('Chicago', '9087', 22),
('Pheonix', '4683', 34),
('Anaheim', '1234', 26),
('Seattle', '7856', 30);