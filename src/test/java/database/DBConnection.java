package database;

import models.Student;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    public void connectToDB(){
        try {
            connect = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/aqa4?" +
                            "user=root&password=gdeVlad1k&useSSL=true&serverTimezone=UTC");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }



    private void writeResultSet(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String lesson = resultSet.getString("lesson");
            boolean completed = resultSet.getBoolean("completed");
            String student_id = resultSet.getString("studentid");
            System.out.println("ID: " + id);
            System.out.println("LESSON: " + lesson);
            System.out.println("COMPLETED: " + completed);
            System.out.println("STUDENT_ID: " + student_id);

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

    public ArrayList<Student> getData(String tableName) {
        // Result set get the result of the SQL query
        ArrayList<Student> students = new ArrayList();
        try {
            resultSet = statement.executeQuery("select * from " + tableName);
            while(resultSet.next()) {
                Student student = new Student(
                        resultSet.getString("id"),
                        resultSet.getString("name"));
                students.add(student);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return students;
    }

    public void createStudent(String name) {
        try {
            PreparedStatement preparedStatement =
                    connect.prepareStatement("INSERT INTO aqa4.students (name) VALUES(?)");
            preparedStatement.setString(1, name);
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

