package test.base;

import org.junit.Test;
import pages.*;

import static org.junit.Assert.assertEquals;

public class FooterTest {

    private StartPage startPage;

    public FooterTest() {
        startPage = new StartPage().openPage(StartPage.class);
    }

    @Test
    public void topMenuLinks() {
        assertEquals(true, startPage.getFooterPage().aboutPageLabelPresent());
        assertEquals(true, startPage.getFooterPage().aboutLagrummetLabelPresent());
        assertEquals(true, startPage.getFooterPage().contactUsLabelPresent());
    }

    @Test
    public void cookiesLink() {
        CookiesPage cookiesPage = startPage.getFooterPage().clickOnCookiesPage();

        assertEquals(true, cookiesPage.isAt());
    }

    @Test
    public void technicalInformationLink() {
        TechnicalInformationPage technicalInformationPage = startPage.getFooterPage().clickOnTechnicalInformationLink();

        assertEquals(true, technicalInformationPage.isAt());
    }

    @Test
    public void lagrummetLinksLink() {
        LagrummetLinksPage lagrummetLinksPage = startPage.getFooterPage().clickOnLagrummetLinksLink();

        assertEquals(true, lagrummetLinksPage.isAt());
    }

    @Test
    public void legalSourcesLink() {
        LegalSourcesPage legalSourcesPage = startPage.getFooterPage().clickOnLegalSourcesLink();

        assertEquals(true, legalSourcesPage.isAt());
    }

    @Test
    public void legalInformationLink() {
        LegalInformationPage legalInformationPage = startPage.getFooterPage().clickOnLegalInformationLink();

        assertEquals(true, legalInformationPage.isAt());
    }

    @Test
    public void aboutUsLink() {
        AboutPage aboutPage = startPage.getFooterPage().clickOnAboutLink();

        assertEquals(true, aboutPage.isAt());
    }

    @Test
    public void contactUsLink() {
        ContactUsPage contactUsPage = startPage.getFooterPage().clickOnContactUsLink();

        assertEquals(true, contactUsPage.isAt());
    }

    @Test
    public void emailResponseLink() {
        EmailResponsePage emailResponsePage = startPage.getFooterPage().clickOnEmailResponseLink();

        assertEquals(true, emailResponsePage.isAt());
    }
}
