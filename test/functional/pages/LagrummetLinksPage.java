package pages;

import org.openqa.selenium.By;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class LagrummetLinksPage extends BasePage<LagrummetLinksPage> {
    @Override
    public boolean isAt() {
        return  SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Länkar du hittar på lagrummet.se')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
