DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS storeowner;
DROP TABLE IF EXISTS store_has_storeowner;
DROP TABLE IF EXISTS store;
DROP TABLE IF EXISTS user_loves_store;

CREATE TABLE user (
  UserId INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  CreateTime NUMERIC,
  Gender TEXT,
  Birthday NUMERIC,
);


CREATE TABLE storeowner(
  UserId INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT UNIQUE NOT NULL,
  password TEXT NOT NULL,
  CreateTime NUMERIC,
  Gender TEXT,
  Birthday NUMERIC
);


CREATE TABLE store_has_storeowner(
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  StoreId INTEGER NOT NULL,
  StoreOwnerId INTEGER NOT NULL
);


CREATE TABLE store(
  StoreId INTEGER PRIMARY KEY AUTOINCREMENT,
  StoreName TEXT NOT NULL,
  StoreLatitude REAL NOT NULL,
  StoreLongitude REAL NOT NULL,
  StoreDescription TEXT
);

CREATE TABLE user_loves_store(
  Id INTEGER PRIMARY KEY AUTOINCREMENT,
  StoreId INTEGER NOT NULL,
  UserId INTEGER NOT NULL
);

Create TABLE store_images(
  StoreId INTEGER NOT NULL,
  ImageName TEXT NOT NULL
);



