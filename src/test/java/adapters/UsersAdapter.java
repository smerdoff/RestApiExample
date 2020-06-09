package adapters;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import models.JobUser;
import models.SingleUser;
import models.UsersList;
import org.apache.http.protocol.HTTP;

import static io.restassured.RestAssured.given;

public class UsersAdapter extends MainAdapter{

    public JobUser post(JobUser user) {

        Response response =
        given()
                .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                .body(gson.toJson(user))
        .when()
            .post("https://reqres.in/api/users")
        .then()
                .statusCode(201)
                .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), JobUser.class);
    }

    public UsersList get(int page) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get(String.format("https://reqres.in/api/users?page=%s", page))
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), UsersList.class);
    }

    public UsersList getDelayedResponse(int delay) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                        .when()
                        .get(String.format("https://reqres.in/api/users?delay=%s", delay))
                        .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), UsersList.class);
    }

    public SingleUser getUser(int id) {

        Response response =
                given()
                        .header(HTTP.CONTENT_TYPE, ContentType.JSON)
                        .log().all()
                .when()
                        .get(String.format("https://reqres.in/api/users/%s", id))
                .then()
                        .log().all()
                        .statusCode(200)
                        .contentType(ContentType.JSON).extract().response();

        return gson.fromJson(response.asString().trim(), SingleUser.class);
    }


}
