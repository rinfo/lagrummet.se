package pages;

import org.openqa.selenium.By;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class LegalInformationPage extends BasePage<LegalInformationPage> {
    @Override
    public boolean isAt() {
        return  SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Juridisk information')]")));

    }

    @Override
    public String getUrl() {
        return null;
    }
}
