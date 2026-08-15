package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pages.BankPage;
import utils.ConfigReader;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.closeWebDriver;

public class FinancialReportsTest {

    private BankPage bankPage;

    @BeforeEach
    void setup() {
        Configuration.browser = ConfigReader.get("browser");
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        Configuration.timeout = Long.parseLong(ConfigReader.get("timeout"));
        bankPage = new BankPage();
    }

    @Test
    @DisplayName("Verify 2026 Financial Reports section is present")
    void verifyFinancialReports() {
        bankPage.openPage();
        bankPage.acceptCookies();
        bankPage.clickHamburgerMenu();
        bankPage.openAboutUs();
        bankPage.openFinancialReports();
        bankPage.getSection2026().shouldBe(visible);
        bankPage.getReportLink().should(exist);
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }
}