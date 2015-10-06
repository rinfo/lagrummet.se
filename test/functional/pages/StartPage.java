package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static setup.SeleniumDriver.getDriver;

public class StartPage extends BasePage {

    @FindBy(linkText = "Lagar och förordningar")
    WebElement lawsAndRegulationsLink;

    public StartPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public boolean isAt() {
        return getDriver().getTitle().equalsIgnoreCase("lagrummet.se - startsida");
    }

    @Override
    public String getUrl() {
        return null;
    }

    public void clickOnlawsAndRegulationsLink() {
        lawsAndRegulationsLink.click();
    }
}
