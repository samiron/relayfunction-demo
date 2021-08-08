DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS groups;

CREATE TABLE groups (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  group_name VARCHAR(250) NOT NULL
);

create table users (
  "id" INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  last_name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL,
  group_id INT DEFAULT NULL
);

insert into groups(group_name) values
    ('First Group'),
    ('Second group');

INSERT INTO users (first_name, last_name, email, group_id) VALUES
  ('Aliko', 'Dangote', 'aliko@mail.com', 1),
  ('Bill', 'Gates', 'bill@outlook.com', 1),
  ('Folrunsho', 'Alakija', 'fol@somewhere.com', null);