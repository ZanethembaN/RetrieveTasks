package portfolio.persistence;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Exercise 3.2
 */
public class DataLoader {
    private final Connection connection;

    /**
     * These are the Genres that must be persisted to the database
     */
    private final Map<String, Genre> genres = Map.of(
            "PROG", new Genre("PROG", "Programming"),
            "BIO", new Genre("BIO", "Biography"),
            "SCIFI", new Genre("SCIFI", "Science Fiction"));

    /**
     * These are the Books that must be persisted to the database
     */
    private final List<Book> books = List.of(
            new Book("Test Driven Development", genres.get("PROG")),
            new Book("Programming in Haskell", genres.get("PROG")),
            new Book("Scatterlings of Africa", genres.get("BIO")));

    /**
     * Create an instance of the DataLoader object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public DataLoader(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Genres` collection into the `Genres` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public boolean insertGenres() {

        String sqlStatement = "INSERT INTO Genres(code, description) VALUES (?,?)";
        try {
            for (Map.Entry<String, Genre> entry : genres.entrySet()) {
                Genre genre = entry.getValue();
                PreparedStatement statement = connection.prepareStatement(sqlStatement);
                statement.setString(1, genre.getCode());
                statement.setString(2, genre.getDescription());
                statement.executeUpdate();
            }
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Books` collection into the `Books` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public List<Book> insertBooks() throws SQLException {

        String sqlStatement = "INSERT INTO Books(title, genre_code) VALUES (?, ?)";

        try {
            for (Book book : books) {
                // Use Statement.RETURN_GENERATED_KEYS to retrieve the generated ID
                PreparedStatement statement = connection.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, book.getTitle());
                statement.setString(2, book.getGenre().getCode());
                statement.executeUpdate();

                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int id = generatedKeys.getInt(1);
                        book.assignId(id);
                    } else {
                        System.err.println("No ID obtained for book: " + book.getTitle());
                    }
                }
            }
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get the last id generated from the prepared statement
     *
     * @param s the prepared statement
     * @return the last id generated
     * @throws SQLException if the id was not generated
     */
    private int getGeneratedId(PreparedStatement s) throws SQLException {
        ResultSet generatedKeys = s.getGeneratedKeys();
        if (!generatedKeys.next()) throw new SQLException("Id was not generated");
        return generatedKeys.getInt(1);
    }
}


