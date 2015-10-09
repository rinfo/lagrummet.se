package pages;

import org.openqa.selenium.By;

import static setup.SeleniumDriver.getDriver;

public class EmailResponsePage extends BasePage<EmailResponsePage> {
    @Override
    public boolean isAt() {
        return isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Så svarar vi på e-post')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
