package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class AboutPage extends BasePage<AboutPage> {
    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Om lagrummet.se')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
