package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.support.PageFactory;
import pages.LawsAndRegulationsPage;
import pages.StartPage;

import java.net.MalformedURLException;

import static org.junit.Assert.assertTrue;
import static setup.SeleniumDriver.getDriver;

public class StartPageMenuTest {

    private StartPage startPage;
    private LawsAndRegulationsPage lawsAndRegulationsPage;

    @Before
    public void setUp() throws Exception {
        startPage = PageFactory.initElements(getDriver(), StartPage.class);
        lawsAndRegulationsPage = PageFactory.initElements(getDriver(), LawsAndRegulationsPage.class);
    }

    @Test
    public void lawsAndRegulationsLink() {
        // When
        startPage.clickOnlawsAndRegulationsLink();

        // Then
        assertTrue(lawsAndRegulationsPage.isAt());
    }

    @After
    public void tearDown() throws MalformedURLException {
        getDriver().quit();
    }
}
