-- SQL script to create the `users` table (if missing) and insert 10 sample users
CREATE TABLE IF NOT EXISTS users (
  userid SERIAL PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  age INT,
  address TEXT,
  qualification TEXT
);

INSERT INTO users (username, password, age, address, qualification) VALUES ('user1','pass1',21,'Address 1','Degree1');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user2','pass2',22,'Address 2','Degree2');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user3','pass3',23,'Address 3','Degree3');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user4','pass4',24,'Address 4','Degree4');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user5','pass5',25,'Address 5','Degree5');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user6','pass6',26,'Address 6','Degree6');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user7','pass7',27,'Address 7','Degree7');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user8','pass8',28,'Address 8','Degree8');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user9','pass9',29,'Address 9','Degree9');
INSERT INTO users (username, password, age, address, qualification) VALUES ('user10','pass10',30,'Address 10','Degree10');
