package pages;

import com.codeborne.selenide.SelenideElement;
import utils.ConfigReader;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class BankPage {

    public void openPage() {
        open(ConfigReader.get("ui.url"));
    }

    private static final SelenideElement acceptAllButton =
            $$("button").findBy(text("ACCEPT ALL"));

    public void acceptCookies() {
        acceptAllButton.shouldBe(visible).click();
    }
}