# QA Automation Suite

API and UI test automation framework built with Java 21, RestAssured and Selenide.

## Run Tests

All tests: `./gradlew test`

API only: `./gradlew test --tests "tests.ApiCrudTest"`

UI only: `./gradlew test --tests "tests.FinancialReportsTest"`

## Configuration
Update `src/test/resources/config.properties` to change URLs, browser or logging.

## CI/CD
Tests run automatically on every push. Report available in GitHub Actions artifacts.