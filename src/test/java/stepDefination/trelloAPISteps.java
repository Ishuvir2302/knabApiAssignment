package stepDefination;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;
import org.awaitility.Duration;
import org.hamcrest.Matchers;
import org.json.JSONObject;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.fasterxml.jackson.databind.JsonNode;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import takeAwayEndPoints.trelloBoardEndPoint;
import utilities.ListenerTest;

public class trelloAPISteps extends ListenerTest {
    private Response response;
    private Response response1;
    private String boardId;
    public static Logger loger = LogManager.getLogger("trello");
    public String boardName;
    private String newBoardID;
    JSONObject jsonResponse;

    static ResourceBundle getfilepath() {
        ResourceBundle filepath = ResourceBundle.getBundle("config");
        return filepath;
    }

    @When("GET request is made to the Trello API")
    public void get_request_is_made_to_the_trello_api() throws IOException {
        Path path = Paths.get("./AdequateData/trelloConfig.txt");
        Object[] trelloCon = Files.readAllLines(path).toArray();
        String key = trelloCon[0].toString().split("ApiKey:")[1];
        String token = trelloCon[1].toString().split("Token:")[1];
        boardId = trelloCon[2].toString().split("ValidBoardID:")[1];
        response = trelloBoardEndPoint.getBoardDetails(key, token, boardId);
    }

    @Then("verify response status should be {int}")
    public void verifyStatusCode(int statuscode) {
        loger.log(Level.INFO, "*****testGetBoard*****");
        Awaitility.await().atMost(Duration.TEN_SECONDS).pollInterval(Duration.FIVE_SECONDS)
                .until(() -> response.statusCode() == 200);
        assertTrue(response.getStatusCode() == 200, "Invalid status code: " + response.getStatusCode());
    }

    @Then("verify response should have {string} header as {string}")
    public void verifyResponseHeader(String headerName, String headerValue) {
        String actualContentType = response.getHeader(headerName);
        assertEquals(headerValue, actualContentType);
        loger.log(Level.INFO, "*****TrelloBoardHeaderValidated*****" + actualContentType);
    }

    @Then("verify status line code should be {string}")
    public void verifyStatusLineCode(String statusLine) {
        loger.log(Level.INFO, "StatusLineCode" + response.getStatusLine());
        response.then().assertThat().statusLine(statusLine);
        response.then().log().all();
        loger.log(Level.INFO, "*****StatuLineValidated*****");
    }

    @Then("verify response body should show correct ID")
    public void verifyResponseBoardID() {
        JsonNode responseBody = response.getBody().as(JsonNode.class);
        assertTrue(responseBody.has("id"));
        String actualBoardID = responseBody.get("id").asText();
        assertEquals(boardId, actualBoardID);
        loger.log(Level.INFO, "*****TrelloBoardIdValidated*****" + actualBoardID);
    }

    @Then("verify response time is less than {int} ms")
    public void verifyResponseTime(int expectedTime) {
        long actualResponseTime = response.time();
        System.out.println("Response time" + actualResponseTime);
        assertTrue(actualResponseTime < expectedTime);
    }

    @Then("verify the response schema")
    public void verifyJsonSchema() {
        response.then().assertThat().body(matchesJsonSchemaInClasspath("trelloBoard_schema.json"));
        extentTest.info("Response body:");
        extentTest.log(Status.PASS, MarkupHelper.createCodeBlock(response.asPrettyString(), CodeLanguage.JSON));
        loger.log(Level.INFO, "*****SchemaValidated*****");
    }

    @Given("I created new board with POST Request")
    public void createNewBoard() throws IOException {
        Path path = Paths.get("./AdequateData/trelloConfig.txt");
        Object[] trelloCon = Files.readAllLines(path).toArray();
        String key = trelloCon[0].toString().split("ApiKey:")[1];
        String token = trelloCon[1].toString().split("Token:")[1];
        response = trelloBoardEndPoint.getBoardDetails(key, token, boardId);
    }

    @Given("I want to Board with {string} name")
    public void i_want_to_create_board_with_name(String name) {
        boardName = name;
        loger.log(Level.INFO, "*****BoardNameCreated*****" + boardName);
        System.out.println("boardName     " + boardName);
    }

    @When("I send a POST request to Trello API")
    public void createBoard() throws IOException {
        Path path = Paths.get("./AdequateData/trelloConfig.txt");
        Object[] trelloCon = Files.readAllLines(path).toArray();
        String key = trelloCon[0].toString().split("ApiKey:")[1];
        String token = trelloCon[1].toString().split("Token:")[1];
        response = trelloBoardEndPoint.postCreateBoard(key, token, boardName);
        loger.log(Level.INFO, "*****POSTRequestSent*****");

    }

    @Then("verify response body contains the board id")
    public void verify_response_body_contains_the_board_id() {
        newBoardID = response.jsonPath().getString("id");
        assert newBoardID != null && !newBoardID.isEmpty();
        loger.log(Level.INFO, "*****`CreatedID*****" + newBoardID);
        extentTest.log(Status.PASS, MarkupHelper.createCodeBlock(response.asPrettyString(), CodeLanguage.JSON));
    }

    @Then("verify board is created with {string} name")
    public void verify_board_is_created_with_name(String boardName) {
        String name = response.jsonPath().getString("name");
        assertEquals(boardName, name, "Name should match the expected name");
        loger.log(Level.INFO, "*****`Name Matched*****" + name);
        extentTest.log(Status.PASS, "Trello board is created ");

    }

    @When("I send a DELETE request to the Trello API")
    public void sendDeleteRequest() throws IOException {
        createBoard();
        verify_response_body_contains_the_board_id();
        Path path = Paths.get("./AdequateData/trelloConfig.txt");
        Object[] trelloCon = Files.readAllLines(path).toArray();
        String key = trelloCon[0].toString().split("ApiKey:")[1];
        String token = trelloCon[1].toString().split("Token:")[1];
        loger.log(Level.INFO, "*****`Board ID going to be deleted*****" + newBoardID);
        response1 = trelloBoardEndPoint.deleteCreatedBoard(key, token, newBoardID);
        loger.log(Level.INFO, "*****DeleteRequestSent*****");
        extentTest.log(Status.PASS, "Trello board is deleted ");
    }

    @Then("Verify the delete response body")
    public void verifyDeletedBody() {
        response1.then().assertThat().body("_value", Matchers.nullValue());
        loger.log(Level.INFO, "*****DeleteRequestSent*****");
        extentTest.log(Status.PASS, "Response body is null");
    }

}
