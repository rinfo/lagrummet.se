package pages;

import org.openqa.selenium.By;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class SearchHelpPage extends BasePage<SearchHelpPage> {

    @Override
    public boolean isAt() {
        return SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Sökhjälp - Hittade du inte vad du sökte?')]")));
    }

    @Override
    public String getUrl() {
        return null;
    }
}
