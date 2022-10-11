package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbDAO {

    private static final String url = "jdbc:mysql://localhost:3306/mvdb";

    private static Connection connection = null;

    private static Connection makeConnection() {
        final String user = USER;
        final String password = PASSWORD;

        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, user, password);

                System.out.println("Adatbázis sikeresen megnyitva");
            } catch (ClassNotFoundException | SQLException e) {
                System.out.println("Hiba az adatbázis megnyitásakor:");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static List<Map<String, Object>> executeQuery(String sqlQuery) {
        try (PreparedStatement statement = makeConnection().prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS)) {
            statement.execute();
            ResultSet rs = statement.executeQuery(sqlQuery);
            ResultSetMetaData md = rs.getMetaData();
            int cols = md.getColumnCount();
            List<Map<String, Object>> data = new ArrayList<>();
            while(rs.next()) {
                Map<String, Object> row = new HashMap<>(cols);
                for(int i = 1; i <= cols; ++i) {
                    row.put(md.getColumnName(i), rs.getObject(i));
            }
                data.add(row);
            }
            rs.close();
            statement.close();
//            ResultSet keys = statement.getGeneratedKeys();
//
//            while (keys.next()) {
//                System.out.println("Generated: " + keys.getString(1));
//            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void executeUpdate(String sqlQuery) {
        try (PreparedStatement statement = makeConnection().prepareStatement(sqlQuery)) {
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}