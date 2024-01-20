package environment.utilities;

import java.sql.*;

public final class DatabaseHandler{

    public DatabaseHandler(){
    }

    private static final DatabaseHandler dbHandler = new DatabaseHandler();
    private static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "testdb.db";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Fehler beim Laden des JDBC-Treibers");
            e.printStackTrace();
        }
    }

    public static DatabaseHandler getInstance(){
        return dbHandler;
    }

    private void initDBConnection() {
        if (connection != null){
            return;
        }

        try {
            System.out.println("Creating Database Connection...");
            DatabaseHandler.connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);

            if (!connection.isClosed()){
                System.out.println("Database connected.");
            }

        } catch (SQLException e) {
            System.err.println("Couldn't connect to Database");
            e.printStackTrace();
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                    if (connection.isClosed()) {
                        System.out.println("Connection to Database closed");
                    }
                }
            } catch (SQLException e) {
                System.err.println("Couldn't close Database Connection");
                e.printStackTrace();
            }
        }));
    }


    private void handleDB() {
        try {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("DROP TABLE IF EXISTS books;");
            stmt.executeUpdate("CREATE TABLE books (author, title, publication, pages, price);");
            stmt.execute("INSERT INTO books (author, title, publication, pages, price) VALUES ('Paulchen Paule', 'Paul der Penner', " + Date.valueOf("2001-05-06") + ", '1234', '5.67')");

            PreparedStatement ps = connection
                    .prepareStatement("INSERT INTO books VALUES (?, ?, ?, ?, ?);");

            ps.setString(1, "Willi Winzig");
            ps.setString(2, "Willi's Wille");
            ps.setDate(3, Date.valueOf("2011-05-16"));
            ps.setInt(4, 432);
            ps.setDouble(5, 32.95);
            ps.addBatch();

            ps.setString(1, "Anton Antonius");
            ps.setString(2, "Anton's Alarm");
            ps.setDate(3, Date.valueOf("2009-10-01"));
            ps.setInt(4, 123);
            ps.setDouble(5, 98.76);
            ps.addBatch();

            connection.setAutoCommit(false);
            ps.executeBatch();
            connection.setAutoCommit(true);

            ResultSet rs = stmt.executeQuery("SELECT * FROM books;");
            while (rs.next()) {
                System.out.println("Autor = " + rs.getString("author"));
                System.out.println("Titel = " + rs.getString("title"));
                System.out.println("Erscheinungsdatum = "
                        + rs.getDate("publication"));
                System.out.println("Seiten = " + rs.getInt("pages"));
                System.out.println("Preis = " + rs.getDouble("price"));
            }
            rs.close();

//            stmt.close();
//            ps.close();
            connection.close();
        } catch (SQLException e) {
            System.err.println("Couldn't handle DB-Query");
            e.printStackTrace();
        }
    }

    private boolean createWeatherStationTable() {
        boolean success = false;
        final String createTable = """
                CREATE TABLE weather_stations (
                    id TEXT PRIMARY KEY NOT NULL,
                    name TEXT NOT NULL,
                    date INTEGER NOT NULL
                );
                """;

        try(Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTable);
            success = true;

        } catch (SQLException e) {
            System.err.println("Couldn't create weather_stations table");
            e.printStackTrace();
        }

        return success;
    }


    public static void main(String[] args) {
        DatabaseHandler dbc = DatabaseHandler.getInstance();
        dbc.initDBConnection();
        System.out.println("Creating weather_stations table...");
        System.out.println(dbc.createWeatherStationTable() ? "Success!" : "Failed!");
        dbc.handleDB();
    }


}