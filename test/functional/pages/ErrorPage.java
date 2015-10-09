package pages;

import org.openqa.selenium.By;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class ErrorPage extends BasePage<ErrorPage> {
    @Override
    public boolean isAt() {
        return  SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Sidan gick inte att hitta')]")));
    }

    @Override
    public String getUrl() {
        return "/bad_url";
    }
}
