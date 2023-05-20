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
  language VARCHAR(50) NOT NULL
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