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
        assertEquals(true, startPage.aboutPageLabelPresent());
        assertEquals(true, startPage.aboutLagrummetLabelPresent());
        assertEquals(true, startPage.contactUsLabelPresent());
    }

    @Test
    public void cookiesLink() {
        CookiesPage cookiesPage = startPage.clickOnCookiesPage();

        assertEquals(true, cookiesPage.isAt());
    }

    @Test
    public void technicalInformationLink() {
        TechnicalInformationPage technicalInformationPage = startPage.clickOnTechnicalInformationLink();

        assertEquals(true, technicalInformationPage.isAt());
    }

    @Test
    public void lagrummetLinksLink() {
        LagrummetLinksPage lagrummetLinksPage = startPage.clickOnLagrummetLinksLink();

        assertEquals(true, lagrummetLinksPage.isAt());
    }

    @Test
    public void legalSourcesLink() {
        LegalSourcesPage legalSourcesPage = startPage.clickOnLegalSourcesLink();

        assertEquals(true, legalSourcesPage.isAt());
    }

    @Test
    public void legalInformationLink() {
        LegalInformationPage legalInformationPage = startPage.clickOnLegalInformationLink();

        assertEquals(true, legalInformationPage.isAt());
    }

    @Test
    public void aboutUsLink() {
        AboutPage aboutPage = startPage.clickOnAboutLink();

        assertEquals(true, aboutPage.isAt());
    }

    @Test
    public void contactUsLink() {
        ContactUsPage contactUsPage = startPage.clickOnContactUsLink();

        assertEquals(true, contactUsPage.isAt());
    }

    @Test
    public void emailResponseLink() {
        EmailResponsePage emailResponsePage = startPage.clickOnEmailResponseLink();

        assertEquals(true, emailResponsePage.isAt());
    }
}
