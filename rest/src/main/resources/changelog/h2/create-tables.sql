DROP TABLE IF EXISTS person_table;

CREATE TABLE person_table (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  nickname VARCHAR(250) NOT NULL,
  age VARCHAR(250) DEFAULT NULL,
  birthday DATE NOT NULL,
);