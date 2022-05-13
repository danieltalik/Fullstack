DROP TABLE IF EXISTS birthday;
DROP TABLE IF EXISTS nicknames;

CREATE TABLE birthday (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  last_name VARCHAR(250) NOT NULL,
  age VARCHAR(250) DEFAULT NULL,
  birthday DATE NOT NULL,
);

CREATE TABLE nickname (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  nickname VARCHAR(250) NOT NULL,
);