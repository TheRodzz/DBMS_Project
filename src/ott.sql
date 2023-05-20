-- creating the database
-- CREATE DATABASE ottplatform;
-- USE ottplatform;

-- creating tables
CREATE TABLE User (
    uid INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    isAdmin BOOL NOT NULL
);

CREATE TABLE Media (
    tid INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(50) NOT NULL,
    release_date DATE NOT NULL
);

CREATE TABLE Languages(
    tid INT, 
    language VARCHAR(50) NOT NULL,
    PRIMARY KEY (tid,language),
    FOREIGN KEY (tid) REFERENCES Media (tid) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Ratings (
    tid INT NOT NULL,
    uid INT NOT NULL,
    rating DECIMAL(2,1) NOT NULL,
    PRIMARY KEY(tid,uid),
    FOREIGN KEY (tid) REFERENCES Media(tid) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (uid) REFERENCES User(uid) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE View_History (
    tid INT NOT NULL,
    uid INT NOT NULL,
    watched_on TIMESTAMP,
    PRIMARY KEY(tid, uid, watched_on),
    FOREIGN KEY (tid) REFERENCES Media(tid) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (uid) REFERENCES User(uid) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Populating User table
INSERT INTO User (name, user_name, password, isAdmin)
VALUES ('Rajesh Kumar', 'rajesh123', 'password1', 1),
       ('Priya Sharma', 'priya456', 'password2', 1),
       ('Amit Patel', 'amit789', 'password3', 0),
       ('Sneha Gupta', 'sneha101', 'password4', 0),
       ('Naveen Reddy', 'naveen999', 'password5', 0),
       ('Anita Verma', 'anita246', 'password6', 0),
       ('Rahul Singh', 'rahul789', 'password7', 0),
       ('Pooja Mishra', 'pooja111', 'password8', 0),
       ('Manish Mehta', 'manish456', 'password9', 0),
       ('Divya Sharma', 'divya789', 'password10', 0);

-- Populating Media table
INSERT INTO Media (title, release_date) VALUES
  ('Movie A', '2022-01-10'),
  ('Movie B', '2021-07-22'),
  ('TV Show X', '2020-05-05');

-- Populating Languages table
INSERT INTO Languages (tid, language) VALUES
  (1, 'English'),
  (1, 'Spanish'),
  (2, 'English'),
  (3, 'English'),
  (3, 'French');

-- Populating Ratings table
INSERT INTO Ratings (tid, uid, rating) VALUES
  (1, 1, 4.5),
  (1, 2, 3.8),
  (2, 1, 4.2),
  (2, 3, 4.0),
  (3, 2, 3.5),
  (3, 3, 4.3);

-- Populating View_History table
INSERT INTO View_History (tid, uid, watched_on) VALUES
  (1, 1, '2022-02-15 10:30:00'),
  (1, 2, '2022-02-18 14:45:00'),
  (2, 1, '2021-08-01 20:15:00'),
  (3, 3, '2020-07-10 12:00:00');

