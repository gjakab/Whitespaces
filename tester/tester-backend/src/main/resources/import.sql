INSERT INTO USERS (ID, VERSION, FIRSTNAME, LASTNAME, SCHOOL, EMAIL, PASSWORD, ROLE) VALUES (0, 1, 'Harry', 'Potter', 'Roxfort', 'HarryTheBestWiz@rd.com', 'Salvio_hexia', 'STUDENT');
-- Teszt Elek password: 1234
INSERT INTO USERS (ID, VERSION, FIRSTNAME, LASTNAME, SCHOOL, EMAIL, PASSWORD, ROLE) VALUES (1, 1, 'Teszt', 'Elek', 'Roxfort', 'teszt@elek.com', '$2a$10$xhIdCuPXVaJr8tEtp53CaOqPfoSHLOzJZ7eHTG6i0cmfihrFTbFca', 'TEACHER');


-- Quizek
INSERT INTO ASSESSMENTS (ID, VERSION, CREATION_DATE, NAME, STAT, USER_ID) VALUES (1, 1, '2018-04-07', 'Nagyon durva quiz 1', '99.9', 1);
INSERT INTO ASSESSMENTS (ID, VERSION, CREATION_DATE, NAME, STAT, USER_ID) VALUES (2, 1, '2018-04-08', 'Nagyon durva quiz 2', '89.9', 1);
INSERT INTO ASSESSMENTS (ID, VERSION, CREATION_DATE, NAME, STAT, USER_ID) VALUES (3, 1, '2018-04-09', 'Nagyon durva quiz 3', '79.9', 1);
INSERT INTO ASSESSMENTS (ID, VERSION, CREATION_DATE, NAME, STAT, USER_ID) VALUES (4, 1, '2018-04-10', 'Nagyon durva quiz 4', '69.9', 1);
INSERT INTO ASSESSMENTS (ID, VERSION, CREATION_DATE, NAME, STAT, USER_ID) VALUES (5, 1, '2018-04-11', 'Nagyon durva quiz 5', '59.9', 1);