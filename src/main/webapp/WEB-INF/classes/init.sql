CREATE SCHEMA IF NOT EXISTS clevertec_system;

SET search_path TO clevertec_system;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS users
(
    id         UUID DEFAULT uuid_generate_v4(),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    mail       VARCHAR(255),
    age        INT,
    PRIMARY KEY (id)
);

INSERT INTO users (id, first_name, last_name, mail, age)
VALUES ('37bc2b04-8607-11ee-b9d1-0242ac120002', 'John', 'Doe', 'john.doe@mail.ru', 40),
       ('3ef4c1f6-8607-11ee-b9d1-0242ac120002', 'Jane', 'Smith', 'jane.smith@gmail.com', 30),
       ('43c4ec92-8607-11ee-b9d1-0242ac120002', 'Alice', 'Johnson', 'alice.johnson@gmail.com', 28),
       ('4a2198e8-8607-11ee-b9d1-0242ac120002', 'Bob', 'Williams', 'bob.williams@yahoo.com', 35),
       ('4f7ac3da-8607-11ee-b9d1-0242ac120002', 'Eva', 'Brown', 'eva.brown@hotmail.com', 25),
       ('50c0a9aa-8607-11ee-b9d1-0242ac120002', 'Daniel', 'Miller', 'daniel.miller@gmail.com', 45),
       ('52e6908e-8607-11ee-b9d1-0242ac120002', 'Sophie', 'Davis', 'sophie.davis@yahoo.com', 33),
       ('53df1c6a-8607-11ee-b9d1-0242ac120002', 'Ryan', 'Wilson', 'ryan.wilson@hotmail.com', 29),
       ('556205f8-8607-11ee-b9d1-0242ac120002', 'Emma', 'Moore', 'emma.moore@gmail.com', 22),
       ('5615b3b2-8607-11ee-b9d1-0242ac120002', 'Peter', 'Clark', 'peter.clark@mail.ru', 38),
       ('56f3e2d4-8607-11ee-b9d1-0242ac120002', 'Olivia', 'Walker', 'olivia.walker@yahoo.com', 27),
       ('57e6b7ae-8607-11ee-b9d1-0242ac120002', 'Michael', 'Smith', 'michael.smith@gmail.com', 32),
       ('5878732a-8607-11ee-b9d1-0242ac120002', 'Ava', 'Anderson', 'ava.anderson@hotmail.com', 31),
       ('5923ec72-8607-11ee-b9d1-0242ac120002', 'William', 'Turner', 'william.turner@gmail.com',
        41),
       ('59d7e476-8607-11ee-b9d1-0242ac120002', 'Grace', 'Evans', 'grace.evans@mail.ru', 26),
       ('5a8bbd42-8607-11ee-b9d1-0242ac120002', 'Christopher', 'Moore',
        'christopher.moore@yahoo.com', 34)
ON CONFLICT (id) DO NOTHING;