-- CREATE DATABASE newsportal;
--\c newsportal


SET MODE PostgreSQL;

CREATE TABLE IF NOT EXISTS staff (
 id SERIAL PRIMARY KEY,
 name VARCHAR,
  position VARCHAR,
  role VARCHAR
);



CREATE TABLE IF NOT EXISTS departments (
id SERIAL PRIMARY KEY,
name VARCHAR,
description VARCHAR,
size int
);

CREATE TABLE  IF NOT EXISTS news (
id SERIAL PRIMARY KEY,
news_type VARCHAR,
title VARCHAR,
description VARCHAR

);

CREATE TABLE  IF NOT EXISTS users_departments (
id SERIAL PRIMARY KEY,
user_id INT,
department_id INT
);

--CREATE DATABASE newsportal_test WITH TEMPLATE newsportal;