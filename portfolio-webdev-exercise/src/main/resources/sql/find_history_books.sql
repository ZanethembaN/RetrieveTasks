SELECT COUNT(*)
FROM Books
JOIN Genres ON Books.genre_code = Genres.code
WHERE Genres.description = 'History';
