package database;

import java.sql.*;

public class DBConnection {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void readDataBase() throws Exception {
        try {
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost/tms?"
                            + "user=root&password=password02&userSSL=true");

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from tms.tms_group");
            writeResultSet(resultSet);
        } finally {
            close();
        }
    }


    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            System.out.println("ID: " + id);
            System.out.println("NAME: " + name);
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        }catch (Exception ignored) {
        }
    }

}
