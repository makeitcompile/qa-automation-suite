package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import pages.BankPage;
import utils.ConfigReader;

public class FinancialReportsTest {

    private BankPage bankPage;

    @BeforeEach
    void setup() {
        Configuration.browser = ConfigReader.get("browser");
        Configuration.headless = Boolean.parseBoolean(ConfigReader.get("headless"));
        Configuration.timeout = Long.parseLong(ConfigReader.get("timeout"));
        bankPage = new BankPage();
    }
}