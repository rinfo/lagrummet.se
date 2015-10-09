package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class LegalSourcesPage extends BasePage<LegalSourcesPage> {
    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Rättsinformation - rättskällorna')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
