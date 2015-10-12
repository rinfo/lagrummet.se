package pages.menu;

import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import pages.BasePage;
import pages.common.FooterPage;
import pages.common.HeaderPage;
import pages.common.MenuPage;
import setup.SeleniumDriver;

import static setup.SeleniumDriver.getDriver;

public class FaqPage extends BasePage<FaqPage>{
    private HeaderPage headerPage = PageFactory.initElements(getDriver(), HeaderPage.class);
    private FooterPage footerPage = PageFactory.initElements(getDriver(), FooterPage.class);
    private MenuPage menuPage = PageFactory.initElements(getDriver(), MenuPage.class);

    @Override
    public boolean isAt() {
        return  SeleniumDriver.isDisplayed(getDriver().findElement(By.xpath("//div[@id='content']/article/header/h1[contains(text(), 'Vanliga frågor')]")));
    }

    @Override
    public String getUrl() {
        return "/lar-dig-mer/vanliga-fragor";
    }

    public HeaderPage getHeaderPage() {
        return headerPage;
    }

    public FooterPage getFooterPage() {
        return footerPage;
    }

    public MenuPage getMenuPage() {
        return menuPage;
    }
}
