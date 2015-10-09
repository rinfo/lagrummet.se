package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class TechnicalInformationPage extends BasePage<TechnicalInformationPage> {

    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Teknisk information & användningstips')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
