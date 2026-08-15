package pages;

import utils.ConfigReader;
import static com.codeborne.selenide.Selenide.open;

public class BankPage {

    public void openPage() {
        open(ConfigReader.get("ui.url"));
    }
}