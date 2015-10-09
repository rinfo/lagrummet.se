package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class LegalInformationPage extends BasePage<LegalInformationPage> {
    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Juridisk information')]")));

    }

    @Override
    public String getUrl() {
        return null;
    }
}
