package pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import pages.*;
import setup.SeleniumDriver;

public class FooterPage {

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

    @FindBy(xpath = "//footer[@class='reviewed'][contains(text(), 'Senast granskad')]")
    WebElement latestReviewedLabel;

    public boolean aboutPageLabelPresent() {
        return SeleniumDriver.isDisplayed(aboutPageLabel);
    }

    public boolean aboutLagrummetLabelPresent() {
        return SeleniumDriver.isDisplayed(aboutLagrummetLabel);
    }

    public boolean contactUsLabelPresent() {
        return SeleniumDriver.isDisplayed(contactUsLabel);
    }

    public boolean latestReviewsLabelPresent() {
        return SeleniumDriver.isDisplayed(latestReviewedLabel);
    }

    public CookiesPage clickOnCookiesPage() {
        SeleniumDriver.clickOn(cookiesLink);
        return new CookiesPage();
    }

    public TechnicalInformationPage clickOnTechnicalInformationLink() {
        SeleniumDriver.clickOn(technicalInformationLink);
        return new TechnicalInformationPage();
    }

    public LagrummetLinksPage clickOnLagrummetLinksLink() {
        SeleniumDriver.clickOn(lagrummetLinksLink);
        return new LagrummetLinksPage();
    }

    public LegalSourcesPage clickOnLegalSourcesLink() {
        SeleniumDriver.clickOn(legalSourcesLink);
        return new LegalSourcesPage();
    }

    public LegalInformationPage clickOnLegalInformationLink() {
        SeleniumDriver.clickOn(legalInformationLink);
        return new LegalInformationPage();
    }

    public AboutPage clickOnAboutLink() {
        SeleniumDriver.clickOn(aboutLink);
        return new AboutPage();
    }

    public ContactUsPage clickOnContactUsLink() {
        SeleniumDriver.clickOn(contactUsLink);
        return new ContactUsPage();
    }

    public EmailResponsePage clickOnEmailResponseLink() {
        SeleniumDriver.clickOn(emailResponseLink);
        return new EmailResponsePage();
    }
}
