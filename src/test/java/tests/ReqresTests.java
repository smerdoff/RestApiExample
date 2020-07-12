package tests;

import adapters.ResourcesAdapter;
import adapters.UsersAdapter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.*;
import org.apache.http.protocol.HTTP;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.charset.Charset;

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
            .statusCode(200);
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

    @Test
    public void notFoundUser() {
    String expected = "{}";
    Response response = when()
            .get("https://reqres.in/api/users/23")
            .then()
            .log().all()
            .statusCode(404)
            .contentType(ContentType.JSON).extract().response();
    assertEquals(expected, response.asString().trim());
    }

    @Test
    public void getResourceList() throws FileNotFoundException {
        ResourcesList expectedList;
        expectedList = gson.fromJson(new FileReader("src/test/resources/expectedResources.json"), ResourcesList.class);
        ResourcesList list = new ResourcesAdapter().get();
        assertEquals(list, expectedList);
    }

    @Test
    public void getSingleResource() throws FileNotFoundException {
        SingleResource expectedResource;
        expectedResource = gson.fromJson(new FileReader("src/test/resources/expectedSingleResource.json"), SingleResource.class);
        SingleResource singleResource = new ResourcesAdapter().getResource(2);
        assertEquals(expectedResource,singleResource);
    }

    @Test
    public void notFoundResource() {
        String expected = "{}";
        Response response =
        when()
                .get("https://reqres.in/api/resources/23")
        .then()
                .log().all()
                .statusCode(404)
                .contentType(ContentType.JSON).extract().response();
        assertEquals(expected, response.asString().trim());
    }

    @Test
    public void createUser() {
        given()
                .body("{\"name\":\"morpheus\",\"job\":\"leader\"}")
        .when()
                .post("https://reqres.in/api/users")
        .then()
                .log().all()
                .statusCode(201);
    }

    @Test
    public void putUser() {
        given()
                .body("{\"name\":\"morpheus\",\"job\":\"zion resident\"}")
        .when()
                .put("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void patchUser() {
        given()
                .body("{\"name\":\"morpheus\",\"job\":\"zion resident\"}")
        .when()
                .patch("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void deleteUser() {
        when()
                .delete("https://reqres.in/api/users/2")
        .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void SuccessfulRegister() {

        String expectedResponse = "{\"id\":4,\"token\":\"QpwL5tke4Pnpja7X4\"}";
        Response response =
        given()
                .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"pistol\"}")
                .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                .log().all()
        .when()
                .post("https://reqres.in/api/register")
        .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON).extract().response();
        Assert.assertEquals(expectedResponse, response.asString().trim());
    }

    @Test
    public void UnsuccessfulRegister() {
                given()
                        .body("{\"email\":\"sydney@fife\"}")
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .post("https://reqres.in/api/register")
                .then()
                        .log().all()
                        .statusCode(400)
                        .body("error", equalTo("Missing password"));
    }

    @Test
    public void SuccessfulLogin() {
        String expectedResponse = "{\"token\":\"QpwL5tke4Pnpja7X4\"}";
        Response response =
                given()
                        .body("{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}")
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .post("https://reqres.in/api/login")
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();
        Assert.assertEquals(expectedResponse, response.asString().trim());
    }

    @Test
    public void getDelayedResponse() throws FileNotFoundException {
        UsersList expectedList;
        expectedList = gson.fromJson(new FileReader("src/test/resources/expectedDelayResponse.json"), UsersList.class);
        UsersList list = new UsersAdapter().getDelayedResponse(3);
        assertEquals(list, expectedList);
    }


}




