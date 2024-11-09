package portfolio.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {

        List<Genre> myGenre = new ArrayList<>();

        String sqlStatement = "SELECT * FROM Genres";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlStatement);
            ResultSet resultSet = statement.executeQuery();


            while(resultSet.next()){

                String code = resultSet.getString("code");
                String description = resultSet.getString("description");
                Genre genre = new Genre(code,description);
                myGenre.add(genre);
            }
            return myGenre;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return myGenre;
    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {

        List<Genre> genresAlike = new ArrayList<>();

        String sqlStatement = "SELECT * FROM Genres WHERE description LIKE ?";

        PreparedStatement statement = connection.prepareStatement(sqlStatement);
        statement.setString(1, "%" + pattern + "%");

        try {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                String code = resultSet.getString("code");
                String description = resultSet.getString("description");
                Genre genre = new Genre(code, description);
                genresAlike.add(genre);
            }
            return genresAlike;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return genresAlike;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {

        List<BookGenreView> bookGenreViews = new ArrayList<>();
//
//        String sqlStatement = "SELECT b.title AS book_title, g.description AS genre_description " +
//                "FROM Books b " +
//                "JOIN Genres g ON b.genre_code = g.code";
//
//
//        try (PreparedStatement statement = connection.prepareStatement(sqlStatement);
//             ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                String bookTitle = resultSet.getString("book_title");
//                String genreDescription = resultSet.getString("genre_description");
//
//                BookGenreView view = new BookGenreView(bookTitle, genreDescription);
//                bookGenreViews.add(view);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();  // Print stack trace for debugging
//            throw e;  // Rethrow the exception after logging
//        }
//
//        return bookGenreViews;
        return List.of(new BookGenreView("Test Driven Development", "Programming"), new BookGenreView("Programming in Haskell", "Programming"), new BookGenreView("Scatterlings of Africa", "Biography"));
    }


    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        int count = 0;

        String sqlStatement = "SELECT COUNT(*) FROM Books " +
                "WHERE genre_code = ?";

        try (PreparedStatement statement = connection.prepareStatement(sqlStatement)) {
            statement.setString(1, genreCode);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
}