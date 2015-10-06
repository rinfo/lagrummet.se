package pages;

import org.openqa.selenium.WebDriver;

public abstract class BasePage {

    protected final String BASE_URL = "https://www.beta.lagrummet.se";
    private final WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        driver.get(BASE_URL);
    }

    public abstract boolean isAt();

    public abstract String getUrl();

}
