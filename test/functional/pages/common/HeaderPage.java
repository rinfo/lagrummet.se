package pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.SiteMapPage;
import setup.SeleniumDriver;

public class HeaderPage {

    @FindBy(linkText = "English")
    WebElement englishLink;

    @FindBy(linkText = "Webbkarta")
   protected WebElement siteMapLink;

    @FindBy(linkText = "Lyssna")
    WebElement listenLink;

    public boolean listenLinkPresent() {
        return  SeleniumDriver.isDisplayed(listenLink);
    }

    public boolean englishLinkPresent() {
        return  SeleniumDriver.isDisplayed(englishLink);
    }

    public boolean siteMapLinkPresent() {
        return  SeleniumDriver.isDisplayed(siteMapLink);
    }

    public SiteMapPage clickOnSiteMapLink() {
        SeleniumDriver.clickOn(siteMapLink);
        return new SiteMapPage();
    }
}
