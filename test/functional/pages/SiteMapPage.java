package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class SiteMapPage extends BasePage<SiteMapPage> {

    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Webbkarta över lagrummet.se')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
