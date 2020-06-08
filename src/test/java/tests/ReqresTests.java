package tests;

import adapters.UsersAdapter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.JobUser;
import models.SingleUser;
import models.UsersList;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class ReqresTests extends BaseTest{

    @Test
    public void testList() throws FileNotFoundException {
        UsersList expectedList;
        expectedList = gson.fromJson(new FileReader("src/test/resources/expectedList.json"), UsersList.class);

        UsersList list = new UsersAdapter().get(1);
        assertEquals(list, expectedList);
    }
    
    @Test
    public void createUser() {
        JobUser user = new UsersAdapter().post(new JobUser("name", "job", "", ""));
        System.out.println(user);
    }

    @Test
    public void getUser() {
        String expected = "{\"data\":{\"id\":2,\"email\":\"janet.weaver@reqres.in\",\"first_name\":\"Janet\",\"last_name\":\"Weaver\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/josephstein/128.jpg\"}," +
                "\"ad\":{\"company\":\"StatusCode Weekly\",\"url\":\"http://statuscode.org/\",\"text\":\"A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.\"}}";
        //https://reqres.in/api/users/2
        Response response = when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();
        assertEquals(expected, response.asString().trim());
    }
//////////Homework21
    @Test
    public void getListAPI() {
    given()
            .body("page: 2")
            .when()
            .get("https://reqres.in/api/users?page=2")
            .then()
            .log().all()
            .statusCode(200)
            .body("ad.text",equalTo("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things."));
}

    @Test
    public void getList() throws FileNotFoundException {
        UsersList expectedList;
        expectedList = gson.fromJson(new FileReader("src/test/resources/expectedList2.json"), UsersList.class);
        UsersList list = new UsersAdapter().get(2);
        assertEquals(list, expectedList);
    }

    @Test
    public void getSingleUser() throws FileNotFoundException {
        SingleUser expectedUser;
        expectedUser = gson.fromJson(new FileReader("src/test/resources/expectedUser.json"), SingleUser.class);
        SingleUser singleUser = new UsersAdapter().getUser(2);
        assertEquals(singleUser,expectedUser);
    }

}




