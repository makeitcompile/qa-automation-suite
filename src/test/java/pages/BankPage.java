package pages;

import com.codeborne.selenide.SelenideElement;
import utils.ConfigReader;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankPage {

    public void openPage() {
        open(ConfigReader.get("ui.url"));
    }

    public void acceptCookies() {
        try {
            $$("button").findBy(text("ACCEPT ALL"))
                    .shouldBe(visible, Duration.ofSeconds(12))
                    .click();
        } catch (Exception e) {
            System.out.println("[UI] Cookie banner not found - skipping");
        }
    }

    private static final String HAMBURGER_JS =
            "document.querySelector(\"a[aria-label='Site menu']\").click();";

    public void clickHamburgerMenu() {
        executeJavaScript(HAMBURGER_JS);
        sleep(2000);
    }

    public void openAboutUs() {
        executeJavaScript("Array.from(document.querySelectorAll('a')).find(a => a.textContent.trim() === '" + "About Us" + "')?.click();");
        sleep(1000);
    }

    public void openFinancialReports() {
        executeJavaScript("Array.from(document.querySelectorAll('a')).find(a => a.textContent.trim() === '" + "Financial Reports" + "')?.click();");
        sleep(2000);
    }

    public SelenideElement getSection2026() {
        return $$("h2, h3, h4, div, span").findBy(text("2026"));
    }

    public SelenideElement getReportLink() {
        return $("a[href*='financial-report']");
    }
}