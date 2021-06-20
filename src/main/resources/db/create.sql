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

CREATE TABLE IF NOT EXISTS news (
id SERIAL PRIMARY KEY,
news_type VARCHAR,
title VARCHAR,
description VARCHAR,

);

