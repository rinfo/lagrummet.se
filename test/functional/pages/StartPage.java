package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import pages.common.HeaderPage;

import static setup.SeleniumDriver.getDriver;

public class StartPage extends BasePage<StartPage> {

    private HeaderPage headerPage = PageFactory.initElements(getDriver(), HeaderPage.class);

    @FindBy(linkText = "Kakor (cookies)")
    WebElement cookiesLink;

    @FindBy(linkText = "Teknisk information & användningstips")
    WebElement technicalInformationLink;

    @FindBy(linkText = "Länkar du hittar på lagrummet.se")
    WebElement lagrummetLinksLink;

    @FindBy(linkText = "Rättsinformation - rättskällorna")
    WebElement legalSourcesLink;

    @FindBy(linkText = "Juridisk information")
    WebElement legalInformationLink;

    @FindBy(linkText = "Om lagrummet.se")
    WebElement aboutLink;

    @FindBy(linkText = "Kontakta oss")
    WebElement contactUsLink;

    @FindBy(linkText = "Så svarar vi på e-post")
    WebElement emailResponseLink;

    @FindBy(xpath = "//footer[@id='siteFooter']/ul/li[contains(text(), 'Om webbplatsen')]")
    WebElement aboutPageLabel;

    @FindBy(xpath = "//footer[@id='siteFooter']/ul/li[contains(text(), 'Om lagrummet.se')]")
    WebElement aboutLagrummetLabel;

    @FindBy(xpath = "//footer[@id='siteFooter']/ul/li[contains(text(), 'Kontakta oss')]")
    WebElement contactUsLabel;

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

    public boolean aboutPageLabelPresent() {
        return isDisplayed(aboutPageLabel);
    }

    public boolean aboutLagrummetLabelPresent() {
        return isDisplayed(aboutLagrummetLabel);
    }

    public boolean contactUsLabelPresent() {
        return isDisplayed(contactUsLabel);
    }

    public CookiesPage clickOnCookiesPage() {
        clickOn(cookiesLink);
        return new CookiesPage();
    }

    public TechnicalInformationPage clickOnTechnicalInformationLink() {
        clickOn(technicalInformationLink);
        return new TechnicalInformationPage();
    }

    public LagrummetLinksPage clickOnLagrummetLinksLink() {
        clickOn(lagrummetLinksLink);
        return new LagrummetLinksPage();
    }

    public LegalSourcesPage clickOnLegalSourcesLink() {
        clickOn(legalSourcesLink);
        return new LegalSourcesPage();
    }

    public LegalInformationPage clickOnLegalInformationLink() {
        clickOn(legalInformationLink);
        return new LegalInformationPage();
    }

    public AboutPage clickOnAboutLink() {
        clickOn(aboutLink);
        return new AboutPage();
    }

    public ContactUsPage clickOnContactUsLink() {
        clickOn(contactUsLink);
        return new ContactUsPage();
    }

    public EmailResponsePage clickOnEmailResponseLink() {
        clickOn(emailResponseLink);
        return new EmailResponsePage();
    }


}
