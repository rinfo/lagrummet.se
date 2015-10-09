package pages;

import org.openqa.selenium.support.PageFactory;
import pages.common.FooterPage;
import pages.common.HeaderPage;

import static setup.SeleniumDriver.getDriver;

public class StartPage extends BasePage<StartPage> {

    private HeaderPage headerPage = PageFactory.initElements(getDriver(), HeaderPage.class);
    private FooterPage footerPage = PageFactory.initElements(getDriver(), FooterPage.class);

    @Override
    public boolean isAt() {
        return getDriver().getTitle().equalsIgnoreCase("lagrummet.se - startsida");
    }

    @Override
    public String getUrl() {
        return "";
    }

    public HeaderPage getHeaderPage() {
        return headerPage;
    }

    public FooterPage getFooterPage() {
        return footerPage;
    }
}
