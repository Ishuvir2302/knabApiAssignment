package takeAwayEndPoints;

import static io.restassured.RestAssured.given;

import java.io.IOException;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import routes.Routes;

public class trelloBoardEndPoint {

    public static Response getBoardDetails(String key, String token, String boardID) throws IOException {

        Response response = given().header("Accept", "application/json").queryParam("key", key)
                .queryParam("token", token).when().get(Routes.getBoard_url + boardID);
        return response;
    }

    public static Response isURLValid(String url) {
        Response response = given().get(url);
        return response;

    }

    public static Response postCreateBoard(String key, String token, String boardName) throws IOException {

        Response response = given().queryParam("name", boardName).queryParam("key", key).queryParam("token", token)
                .contentType(ContentType.JSON).post(Routes.getBoard_url);
        return response;
    }

    public static Response deleteCreatedBoard(String key, String token, String boardID) throws IOException {
        System.out.println("Response body " + Routes.getBoard_url + boardID);
        Response response = given().queryParam("key", key).queryParam("token", token)
                .delete(Routes.getBoard_url + boardID);
        return response;
    }

}
