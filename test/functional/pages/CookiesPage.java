package pages;

import org.openqa.selenium.By;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class CookiesPage extends BasePage<CookiesPage>{

    @Override
    public boolean isAt() {
        return  SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Kakor (cookies)')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
