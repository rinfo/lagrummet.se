package pages;

import org.openqa.selenium.support.PageFactory;

import static setup.SeleniumDriver.getDriver;

public abstract class BasePage<T> {

    protected final String BASE_URL = "http://stage.lagrummet.se";

    public T openPage(Class<T> clazz) {
        T page = PageFactory.initElements(getDriver(), clazz);
        getDriver().get(BASE_URL + getUrl());
        return page;
    }
    public abstract boolean isAt();

    public abstract String getUrl();

}
