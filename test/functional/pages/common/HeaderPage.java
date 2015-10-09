package pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.BasePage;
import pages.SiteMapPage;

public class HeaderPage extends BasePage<HeaderPage> {

    @FindBy(linkText = "English")
    WebElement englishLink;

    @FindBy(linkText = "Webbkarta")
   protected WebElement siteMapLink;

    @FindBy(linkText = "Lyssna")
    WebElement listenLink;

    public boolean listenLinkPresent() {
        return isDisplayed(listenLink);
    }

    public boolean englishLinkPresent() {
        return isDisplayed(englishLink);
    }

    public boolean siteMapLinkPresent() {
        return isDisplayed(siteMapLink);
    }

    @Override
    public boolean isAt() {
        return false;
    }

    @Override
    public String getUrl() {
        return null;
    }

    public SiteMapPage clickOnSiteMapLink() {
        clickOn(siteMapLink);
        return new SiteMapPage();
    }
}
