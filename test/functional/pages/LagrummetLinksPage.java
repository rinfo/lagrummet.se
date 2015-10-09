package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class LagrummetLinksPage extends BasePage<LagrummetLinksPage> {
    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Länkar du hittar på lagrummet.se')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
