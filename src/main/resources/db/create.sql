SET MODE PostgreSQL;


CREATE TABLE IF NOT EXISTS staff (
 id int PRIMARY KEY auto_increment,
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

