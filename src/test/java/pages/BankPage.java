package pages;

import com.codeborne.selenide.SelenideElement;
import utils.ConfigReader;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class BankPage {

    public void openPage() {
        open(ConfigReader.get("ui.url"));
    }

    private static final SelenideElement acceptAllButton =
            $$("button").findBy(text("ACCEPT ALL"));

    public void acceptCookies() {
        acceptAllButton.shouldBe(visible).click();
    }

    private static final String HAMBURGER_JS =
            "document.querySelector(\"a[aria-label='Site menu']\").click();";

    public void clickHamburgerMenu() {
        executeJavaScript(HAMBURGER_JS);
        sleep(2000);
    }
}