CREATE TABLE Books(
id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
title TEXT NOT NULL,
genre_code TEXT NOT NULL,

FOREIGN KEY (genre_code) REFERENCES Genres(code)
);