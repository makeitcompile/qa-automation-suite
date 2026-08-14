package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import utils.ConfigReader;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiCrudTest {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_NAME = "doggie";
    private static final String PET_STATUS = "available";
    private static final long MAX_RESPONSE_TIME = 3000L;

    private static final String CREATE_BODY = """
            {
              "id": 0,
              "category": {
                "id": 0,
                "name": "string"
              },
              "name": "%s",
              "photoUrls": [
                "string"
              ],
              "tags": [
                {
                  "id": 0,
                  "name": "string"
                }
              ],
              "status": "%s"
            }
            """.formatted(PET_NAME, PET_STATUS);

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
    }

    @Test
    @Order(1)
    @Tag("positive")
    @DisplayName("Create a pet - 200 OK")
    void createPet() {
        given()
                .contentType(ContentType.JSON)
                .body(CREATE_BODY)
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(200)
                .body("name", equalTo(PET_NAME))
                .body("status", equalTo(PET_STATUS))
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(MAX_RESPONSE_TIME));
    }
}