CREATE TABLE IF NOT EXISTS Patron (
  id INTEGER PRIMARY KEY,
  firstname VARCHAR(30) NOT NULL,
  lastname VARCHAR(30) NOT NULL,
  gender CHAR(1) CHECK(gender in ('M','F')),
  email VARCHAR(80),
  subscriber BOOLEAN DEFAULT FALSE,
  belaycert BOOLEAN DEFAULT FALSE,
  leadcert BOOLEAN DEFAULT FALSE,
  suspension DATE
);
CREATE TABLE IF NOT EXISTS Session (
  id INTEGER PRIMARY KEY,
  memberid INTEGER,
  checkin DATETIME NOT NULL,
  checkout DATETIME,
  FOREIGN KEY(memberid) REFERENCES Patron(id)
);
CREATE TABLE IF NOT EXISTS Inventory (
  id INTEGER PRIMARY KEY,
  type VARCHAR(80) NOT NULL,
  price NUMERIC NOT NULL,
  retiredate DATE NOT NULL
);
CREATE TABLE IF NOT EXISTS Account (
  username VARCHAR(30) PRIMARY KEY,
  password VARCHAR(30) NOT NULL,
  firstname VARCHAR(30) NOT NULL,
  lastname VARCHAR(30) NOT NULL,
  type CHAR(1) CHECK(type in ('A','M'))
);