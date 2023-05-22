-- creating the database
CREATE DATABASE IF NOT EXISTS ottplatform;
USE ottplatform;

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
  release_date DATE NOT NULL,
  duration INT,
  poster_url VARCHAR(255),
  trailer_url VARCHAR(255)
);

CREATE TABLE Languages(
  lid INT AUTO_INCREMENT PRIMARY KEY,
  language VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE Media_Languages(
  tid INT,
  lid INT, 
  PRIMARY KEY (tid,lid),
  FOREIGN KEY (tid) REFERENCES Media (tid) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (lid) REFERENCES Languages (lid) ON UPDATE CASCADE ON DELETE CASCADE
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

CREATE TABLE Genres(
  gid INT AUTO_INCREMENT PRIMARY KEY,
  genre VARCHAR(50)
);

CREATE TABLE Media_Genres(
  gid INT,
  tid INT,
  PRIMARY KEY (gid,tid),
  FOREIGN KEY (gid) REFERENCES Genres(gid) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY (tid) REFERENCES Media(tid) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Populate User table with Indian names
INSERT INTO User (name, user_name, password, isAdmin)
VALUES
    ('Rahul Sharma', 'rahul123', 'password', 0),
    ('Neha Patel', 'neha456', 'password', 0),
    ('Amit Kumar', 'amit789', 'password', 0),
    ('Sneha Gupta', 'sneha321', 'password', 0),
    ('Ravi Singh', 'ravi567', 'password', 0),
    ('Priya Jain', 'priya890', 'password', 0),
    ('Vikram Verma', 'vikram234', 'password', 0),
    ('Anjali Gupta', 'anjali567', 'password', 0),
    ('Deepak Sharma', 'deepak890', 'password', 0),
    ('Pooja Patel', 'pooja123', 'password', 0),
    ('Amita Singh', 'amita456', 'password', 0),
    ('Rajesh Gupta', 'rajesh789', 'password', 0),
    ('Kavita Verma', 'kavita321', 'password', 0),
    ('Manish Jain', 'manish567', 'password', 0),
    ('Smita Sharma', 'smita890', 'password', 0),
    ('Sanjay Patel', 'sanjay234', 'password', 0),
    ('Nisha Verma', 'nisha567', 'password', 0),
    ('Harish Jain', 'harish890', 'password', 0),
    ('Rita Singh', 'rita123', 'password', 0),
    ('Rajeshwari Gupta', 'rajeshwari456', 'password', 0);

-- Populate Media table with Indian movie titles
INSERT INTO Media (title, release_date, duration, poster_url, trailer_url)
VALUES
    ('Dilwale Dulhania Le Jayenge', '1995-10-20', 189, 'http://example.com/poster1.jpg', 'http://example.com/trailer1.mp4'),
    ('Kabhi Khushi Kabhie Gham', '2001-12-14', 210, 'http://example.com/poster2.jpg', 'http://example.com/trailer2.mp4'),
    ('Lagaan', '2001-06-15', 224, 'http://example.com/poster3.jpg', 'http://example.com/trailer3.mp4'),
    ('3 Idiots', '2009-12-25', 170, 'http://example.com/poster4.jpg', 'http://example.com/trailer4.mp4'),
    ('Padmaavat', '2018-01-25', 163, 'http://example.com/poster5.jpg', 'http://example.com/trailer5.mp4'),
    ('Bahubali: The Beginning', '2015-07-10', 159, 'http://example.com/poster6.jpg', 'http://example.com/trailer6.mp4'),
    ('Swades', '2004-12-17', 210, 'http://example.com/poster7.jpg', 'http://example.com/trailer7.mp4'),
    ('Kaho Naa... Pyaar Hai', '2000-01-14', 180, 'http://example.com/poster8.jpg', 'http://example.com/trailer8.mp4'),
    ('Bajrangi Bhaijaan', '2015-07-17', 159, 'http://example.com/poster9.jpg', 'http://example.com/trailer9.mp4'),
    ('Dangal', '2016-12-23', 161, 'http://example.com/poster10.jpg', 'http://example.com/trailer10.mp4'),
    ('PK', '2014-12-19', 153, 'http://example.com/poster11.jpg', 'http://example.com/trailer11.mp4'),
    ('Rang De Basanti', '2006-01-26', 157, 'http://example.com/poster12.jpg', 'http://example.com/trailer12.mp4'),
    ('Chhichhore', '2019-09-06', 146, 'http://example.com/poster13.jpg', 'http://example.com/trailer13.mp4'),
    ('Gully Boy', '2019-02-14', 153, 'http://example.com/poster14.jpg', 'http://example.com/trailer14.mp4'),
    ('Andhadhun', '2018-10-05', 139, 'http://example.com/poster15.jpg', 'http://example.com/trailer15.mp4');

-- Populate Languages table with Indian languages
INSERT INTO Languages (language)
VALUES
    ('Hindi'),
    ('Tamil'),
    ('Telugu'),
    ('Malayalam'),
    ('Kannada'),
    ('Bengali'),
    ('Marathi'),
    ('Gujarati'),
    ('Punjabi'),
    ('Odia'),
    ('Assamese'),
    ('Rajasthani'),
    ('Bhojpuri'),
    ('Urdu');

-- Populate Genres table with movie genres
INSERT INTO Genres (genre)
VALUES
    ('Drama'),
    ('Romance'),
    ('Comedy'),
    ('Action'),
    ('Thriller'),
    ('Adventure'),
    ('Sci-Fi'),
    ('Fantasy'),
    ('Mystery'),
    ('Horror'),
    ('Animation'),
    ('Family');

-- Populate Media_Genres table with movie-genre associations
-- Populate Media_Genres table with movie-genre associations
INSERT INTO Media_Genres (tid, gid)
VALUES
    (1, 1),
    (1, 2),
    (1, 3),
    (1, 4),
    (1, 5),
    (1, 6),
    (1, 7),
    (2, 1),
    (2, 2),
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 12),
    (3, 1),
    (3, 2),
    (3, 3),
    (3, 4),
    (3, 7),
    (4, 1),
    (4, 2),
    (4, 4),
    (4, 7),
    (4, 8),
    (5, 1),
    (5, 2),
    (5, 3),
    (5, 5),
    (5, 6),
    (5, 7),
    (6, 1),
    (6, 4),
    (6, 5),
    (6, 6),
    (7, 1),
    (7, 4),
    (7, 6),
    (7, 7),
    (7, 9),
    (8, 1),
    (8, 2),
    (8, 4),
    (8, 7),
    (8, 11),
    (9, 1),
    (9, 4),
    (9, 7),
    (10, 1),
    (10, 4),
    (10, 7),
    (11, 1),
    (11, 4),
    (11, 7),
    (12, 1),
    (12, 4),
    (12, 7),
    (13, 1),
    (13, 4),
    (13, 6),
    (14, 1),
    (14, 4),
    (14, 6),
    (15, 1),
    (15, 4),
    (15, 6);

INSERT INTO View_History (tid, uid, watched_on)
VALUES
    (1, 1, '2020-01-01 10:00:00'),
    (2, 2, '2020-02-01 11:30:00'),
    (3, 3, '2020-03-01 12:45:00'),
    (4, 4, '2020-04-01 14:15:00'),
    (5, 5, '2020-05-01 15:30:00'),
    (6, 6, '2020-06-01 16:45:00'),
    (7, 7, '2020-07-01 18:00:00'),
    (8, 8, '2020-08-01 19:30:00'),
    (9, 9, '2020-09-01 20:45:00'),
    (10, 10, '2020-10-01 22:00:00'),
    (11, 11, '2020-11-01 23:15:00'),
    (12, 12, '2020-12-01 01:00:00'),
    (13, 13, '2021-01-01 02:30:00'),
    (14, 14, '2021-02-01 03:45:00'),
    (15, 15, '2021-03-01 05:00:00'),
    (1, 16, '2021-04-01 06:15:00'),
    (2, 17, '2021-05-01 07:30:00'),
    (3, 18, '2021-06-01 08:45:00'),
    (4, 19, '2021-07-01 10:00:00'),
    (5, 20, '2021-08-01 11:15:00'),
    (6, 1, '2021-09-01 12:30:00'),
    (7, 2, '2021-10-01 13:45:00'),
    (8, 3, '2021-11-01 15:00:00'),
    (9, 4, '2021-12-01 16:15:00'),
    (10, 5, '2022-01-01 17:30:00'),
    (11, 6, '2022-02-01 18:45:00'),
    (12, 7, '2022-03-01 20:00:00'),
    (13, 8, '2022-04-01 21:15:00'),
    (14, 9, '2022-05-01 22:30:00'),
    (15, 10, '2022-06-01 23:45:00'),
    (1, 11, '2022-07-01 01:00:00'),
    (2, 12, '2022-08-01 02:15:00'),
    (3, 13, '2022-09-01 03:30:00'),
    (4, 14, '2022-10-01 04:45:00'),
    (5, 15, '2022-11-01 06:00:00'),
    (6, 16, '2022-12-01 07:15:00'),
    (7, 17, '2023-01-01 08:30:00'),
    (8, 18, '2023-02-01 09:45:00'),
    (9, 19, '2023-03-01 11:00:00'),
    (10, 20, '2023-04-01 12:15:00'),
    (11, 1, '2023-05-01 13:30:00'),
    (12, 2, '2023-06-01 14:45:00'),
    (13, 3, '2023-07-01 16:00:00'),
    (14, 4, '2023-08-01 17:15:00'),
    (15, 5, '2023-09-01 18:30:00'),
    (1, 6, '2023-10-01 19:45:00'),
    (2, 7, '2023-11-01 21:00:00'),
    (3, 8, '2023-12-01 22:15:00'),
    (4, 9, '2023-01-02 23:30:00'),
    (5, 10, '2023-02-02 00:45:00');

--Populating Media_Languages table
INSERT INTO Media_Languages (tid, lid)
VALUES
    (1, 1), (1, 2), (1, 3),
    (2, 2), (2, 3), (2, 4),
    (3, 3), (3, 4), (3, 5),
    (4, 4), (4, 5), (4, 6),
    (5, 5), (5, 6), (5, 7),
    (6, 6), (6, 7), (6, 8),
    (7, 7), (7, 8), (7, 9),
    (8, 8), (8, 9), (8, 10),
    (9, 9), (9, 10), (9, 11),
    (10, 10), (10, 11), (10, 12),
    (11, 11), (11, 12), (11, 13),
    (12, 12), (12, 13), (12, 14),
    (13, 13), (13, 14), (13, 1),
    (14, 14), (14, 1), (14, 2),
    (15, 1), (15, 2), (15, 3);

-- add random values for rating
INSERT INTO Ratings (tid, uid, rating)
SELECT m.tid, u.uid, ROUND(RAND() * 5, 1) AS rating
FROM Media m
CROSS JOIN User u
WHERE m.tid BETWEEN 1 AND 15
  AND u.uid BETWEEN 1 AND 21
GROUP BY m.tid, u.uid
HAVING COUNT(*) <= 5;
