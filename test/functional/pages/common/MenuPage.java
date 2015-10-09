package pages.common;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import setup.SeleniumDriver;

public class MenuPage {

    @FindBy(linkText = "Lagar och förordningar")
    WebElement lawsAndRegulationsLink;

    @FindBy(linkText = "Myndigheters föreskrifter")
    WebElement regulationsLink;

    @FindBy(linkText = "Förarbeten")
    WebElement groundworksLink;

    @FindBy(linkText = "Rättspraxis")
    WebElement caseLawLinks;

    @FindBy(linkText = "Internationellt material")
    WebElement internationalMaterialLink;

    @FindBy(linkText = "Alla rättskällor")
    WebElement allLegalSourcesLink;

    @FindBy(linkText = "Myndigheters och kommuners ansvar")
    WebElement responsibilitesLink;

    @FindBy(linkText = "Ordlista A–Ö")
    WebElement wordListLink;

    @FindBy(linkText = "Rättsprocessens olika steg")
    WebElement rightProcessLink;

    @FindBy(linkText = "Vanliga frågor")
    WebElement faqLink;

    public boolean lawsAndRegulationsLinkPresent() {
        return SeleniumDriver.isDisplayed(lawsAndRegulationsLink);
    }

    public boolean regulationsLinkPresent() {
        return SeleniumDriver.isDisplayed(regulationsLink);
    }

    public boolean groundworksLinkPresent() {
        return SeleniumDriver.isDisplayed(groundworksLink);
    }

    public boolean caseLawLinksPresent() {
        return SeleniumDriver.isDisplayed(caseLawLinks);
    }

    public boolean internationalMaterialLinkPresent() {
        return SeleniumDriver.isDisplayed(internationalMaterialLink);
    }

    public boolean allLegalSourcesLinkPresent() {
        return SeleniumDriver.isDisplayed(allLegalSourcesLink);
    }

    public boolean responsibilitesLinkPresent() {
        return SeleniumDriver.isDisplayed(responsibilitesLink);
    }

    public boolean wordListLinkPresent() {
        return SeleniumDriver.isDisplayed(wordListLink);
    }

    public boolean rightProcessLinkPresent() {
        return SeleniumDriver.isDisplayed(rightProcessLink);
    }

    public boolean faqLinkPresent() {
        return SeleniumDriver.isDisplayed(faqLink);
    }
}
