package tests;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
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
    private static final int PET_ID = 12345;

    private static final String CREATE_BODY = """
            {
              "id": %d,
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
            """.formatted(PET_ID, PET_NAME, PET_STATUS);

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = ConfigReader.get("base.url");
        if (Boolean.parseBoolean(ConfigReader.get("logging.enabled"))) {
            RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        }
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
                .body("id", equalTo(PET_ID))
                .header("Content-Type", containsString("application/json"))
                .time(lessThan(MAX_RESPONSE_TIME));
    }

    @Test
    @Order(2)
    @Tag("positive")
    @DisplayName("Retrieve a pet - 200 OK")
    void retrievePet() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get(PET_ENDPOINT + "/" + PET_ID)
                .then()
                .statusCode(200)
                .body("name", equalTo(PET_NAME))
                .body("status", equalTo(PET_STATUS))
                .body("id", equalTo(PET_ID))
                .time(lessThan(MAX_RESPONSE_TIME));
    }
}