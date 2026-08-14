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
    private static final String UPDATED_NAME = "doggie updated for update endpoint";
    private static final String UPDATED_STATUS = "updated";

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

    private static final String UPDATE_BODY = """
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
            """.formatted(PET_ID, UPDATED_NAME, UPDATED_STATUS);

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

    @Test
    @Order(3)
    @Tag("positive")
    @DisplayName("Update a pet - 200 OK")
    void updatePet() {
        given()
                .contentType(ContentType.JSON)
                .body(UPDATE_BODY)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(200)
                .body("name", equalTo(UPDATED_NAME))
                .body("status", equalTo(UPDATED_STATUS))
                .time(lessThan(MAX_RESPONSE_TIME));
    }

    @Test
    @Order(4)
    @Tag("positive")
    @DisplayName("Delete a pet - 200 OK")
    void deletePet() {
        given()
                .when()
                .delete(PET_ENDPOINT + "/" + PET_ID)
                .then()
                .statusCode(200)
                .time(lessThan(MAX_RESPONSE_TIME));
    }

    @Test
    @Order(5)
    @Tag("negative")
    @DisplayName("Retrieve non existing pet - 404 Not Found")
    void retrieveNonExistingPet() {
        given()
                .when()
                .get(PET_ENDPOINT + "/000000000")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    @Tag("negative")
    @DisplayName("Delete non existing pet - 404 Not Found")
    void deleteNonExistingPet() {
        given()
                .when()
                .delete(PET_ENDPOINT + "/00000000000000000000000000000000000000000000")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(7)
    @Tag("negative")
    @DisplayName("Create a pet - 405 Invalid Input")
    void createPetWithInvalidInput() {
        given()
                .contentType(ContentType.JSON)
                .body("")
                .when()
                .post(PET_ENDPOINT)
                .then()
                .statusCode(405);
    }

    @Test
    @Order(8)
    @Tag("negative")
    @Disabled("Swagger documents 400 but API returns 404")
    @DisplayName("Retrieve a pet - 400 Invalid ID")
    void retrievePetWithInvalidId() {
        given()
                .when()
                .get(PET_ENDPOINT + "/aql")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(9)
    @Tag("negative")
    @Disabled("Swagger documents 400 but API returns 404")
    @DisplayName("Delete a pet with invalid ID - 400 Bad Request")
    void deletePetWithInvalidId() {
        given()
                .when()
                .delete(PET_ENDPOINT + "/aql")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(10)
    @Tag("negative")
    @Disabled("Swagger documents 400 but API returns 500")
    @DisplayName("Update a pet - 400 Invalid ID")
    void updatePetWithInvalidId() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "id": "abc",
                  "name": "lcfc",
                  "photoUrls": ["string"],
                  "status": "available"
                }
                """)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(400);
    }

    @Test
    @Order(11)
    @Tag("negative")
    @Disabled("Swagger documents 404 but API returns 500")
    @DisplayName("Update a pet - 404 Not Found")
    void updateNonExistingPet() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "id": 999999999096646388674323689999877564532357890977543,
                  "name": "oggie",
                  "photoUrls": ["string"],
                  "status": "available"
                }
                """)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(404);
    }

    @Test
    @Order(12)
    @Tag("negative")
    @Disabled("Swagger documents 405 but API returns 500")
    @DisplayName("Update a pet - 405 Validation Exception")
    void updatePetWithInvalidData() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                {
                  "id": "abc",
                  "name": "doggie",
                  "photoUrls": ["string"],
                  "status": "available"
                }
                """)
                .when()
                .put(PET_ENDPOINT)
                .then()
                .statusCode(405);
    }
}