package pages;

import org.openqa.selenium.WebDriver;

import static setup.SeleniumDriver.getDriver;

public class LawsAndRegulationsPage extends BasePage{

    public LawsAndRegulationsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        return getDriver().getTitle().equalsIgnoreCase("Lagar och förordningar");
    }

    @Override
    public String getUrl() {
       return "/rattsinformation/lagar-och-forordningar";
    }
}
