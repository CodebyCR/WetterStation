package environment.utilities;

import model.Station;
import model.TemperatureModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public final class DatabaseHandler{

    public DatabaseHandler(){
    }

    private static final DatabaseHandler dbHandler = new DatabaseHandler();
    private static Connection connection;
    private static final String DB_PATH = System.getProperty("user.home") + "/" + "weatherStation.db";

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

    private void initDBConnection(final Runnable query) {
        if (connection != null){
            return;
        }

        try {
            System.out.println("Creating Database Connection...");
            DatabaseHandler.connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH);

            if (!connection.isClosed()){
                System.out.println("Database connected.");
                query.run();
            }


            connection.close();
        } catch (SQLException e) {
            System.err.println("Couldn't connect to Database");
            e.printStackTrace();
        }
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
                CREATE TABLE IF NOT EXISTS weather_stations (
                    id TEXT PRIMARY KEY NOT NULL,
                    name TEXT NOT NULL
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

    private boolean createTemperatureModelTable() {
        boolean success = false;
        final String createTable = """
                    CREATE TABLE IF NOT EXISTS temperature_models (
                        id TEXT PRIMARY KEY NOT NULL,
                        stationId TEXT NOT NULL,
                        date DATETIME NOT NULL,
                        temperature REAL NOT NULL
                    );
                """;

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTable);
            success = true;

        } catch (SQLException e) {
            System.err.println("Couldn't create temperature_models table");
            e.printStackTrace();
        }
        return success;
    }

    public static void saveStation(final Station station) {
        final String insertStation = """
                INSERT INTO weather_stations
                    (id, name ) VALUES (?, ? );
                """;

        try (PreparedStatement ps = connection.prepareStatement(insertStation)) {
            ps.setString(1, station.getId());
            ps.setString(2, station.getName());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Couldn't save station");
            e.printStackTrace();
        }
    }

    public static void saveTemperatureModel(final Station station) {
        final TemperatureModel temperatureModel = station.getTemperatureModel();
        final String insertTemperatureModel = """
                INSERT INTO temperature_models
                    (id, stationId, date, temperature)
                VALUES (?, ?, ?, ?);
                """;

        try (PreparedStatement ps = connection.prepareStatement(insertTemperatureModel)) {
            temperatureModel.enumerate( (index, pair) -> {
                try {
                    ps.setString(1, UUID.randomUUID().toString());
                    ps.setString(2, station.getId());
                    ps.setLong(3, pair.first());
                    ps.setDouble(4, pair.second());
                    ps.addBatch();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
            connection.setAutoCommit(false);
            ps.executeBatch();
            connection.setAutoCommit(true);

        } catch (SQLException e) {
            System.err.println("Couldn't save temperature model");
            e.printStackTrace();
        }
    }

    public static void saveData(final Station station) {
        System.out.println("Saving station: " + station.getName() + " to database...");
//        System.out.println(station);

        saveStation(station);
        saveTemperatureModel(station);

    }

    public static ArrayList<Station> loadStations() {
        final ArrayList<Station> stations = new ArrayList<>();
        final String selectStations = """
                SELECT *
                FROM weather_stations;
                """;

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(selectStations);
            while (rs.next()) {
                final String id = rs.getString("id");
                final String name = rs.getString("name");
                stations.add(new Station(id, name));
            }
        } catch (SQLException e) {
            System.err.println("Couldn't load stations");
            e.printStackTrace();
        }
        return stations;
    }

    public static TemperatureModel loadTemperatureModel(final String stationId) {
        final TemperatureModel temperatureModel = new TemperatureModel();
        final String selectTemperatureModel = """
                    SELECT *
                    FROM temperature_models
                    WHERE stationId = ?
                    order by date asc;
                """;

        try (PreparedStatement ps = connection.prepareStatement(selectTemperatureModel)) {
            ps.setString(1, stationId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                final Long date = rs.getLong("date");
                final Double temperature = rs.getDouble("temperature");
                System.out.println("Date: " + date + " Temperature: " + temperature);
                temperatureModel.addEntry(date, temperature);
            }
        } catch (SQLException e) {
            System.err.println("Couldn't load temperature model");
            e.printStackTrace();
        }
        return temperatureModel;
    }

    public void initDB(){
        DatabaseHandler dbc = DatabaseHandler.getInstance();
        dbc.initDBConnection(() -> {
            System.out.println("Creating weather_stations table...");
            System.out.println(dbc.createWeatherStationTable() ? "Success!" : "Failed!");
            System.out.println("Creating temperature_models table...");
            System.out.println(dbc.createTemperatureModelTable() ? "Success!" : "Failed!");
            System.out.println("Station count: " + loadStations().size());
            System.out.println();
        });
    }

}