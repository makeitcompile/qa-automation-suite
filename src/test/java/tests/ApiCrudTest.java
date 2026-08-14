package tests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ApiCrudTest {

    private static final String PET_ENDPOINT = "/pet";
    private static final String PET_NAME = "doggie";
    private static final String PET_STATUS = "available";
    private static final long MAX_RESPONSE_TIME = 3000L;

}