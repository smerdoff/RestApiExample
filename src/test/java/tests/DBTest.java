package tests;

import database.DBConnection;
import models.Student;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class DBTest {
 DBConnection dbConnection = new DBConnection();
    @Test
    public void getDB() throws Exception {
        dbConnection.connectToDB();
        ArrayList <Student> students =  dbConnection.getData("aqa4.students");
        Assert.assertEquals(students.get(0).getName(), "Дмитрий Рак");
    }

@Test
public void create() throws SQLException {
        dbConnection.connectToDB();
        dbConnection.createStudent("OLEG");
}
}
